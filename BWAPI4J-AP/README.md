# BWAPI4J Annotation Processor

This tool is used while compiling BWAPI4J to generate various files for Java and C++.

It generates the Java and C++ part of an entity to be transferred from C++ to Java.
Doing this manually for hundreds of fields is very error prone. Since many fields are 
boolean mixing them up is quiet easy and hard to detect.

## The C++ side
Generated code in C++ looks like this:
```C++
void DataBuffer::addFields(const BWAPI::UnitType &entity) {
  add(entity.getID());

  const auto &abilities = entity.abilities();
  add(abilities.size());
  for (const auto &element : abilities) {
    addId(element);
  }
...
```   
It uses the `DataBuffer` utility class.

## The Java side
Generated code in Java looks like this:

```java
public class UnitImplBridge {
...
  public static final int ID = 0;
  public static final int INITIAL_HIT_POINTS = 1;
...
  /**
   * Called the first time UnitImpl is encountered.
   */
  public void initialize(UnitImpl entity, int[] data, int index) {
    entity.iD = data[index + ID];

    entity.initialHitPoints = data[index + INITIAL_HIT_POINTS];
...
  public int update(UnitImpl entity, int[] data, int index) {
    index++;// Skipping 'iD'
    index++;// Skipping 'initialHitPoints'
...
    index = WeaponBridge.update(entity.airWeapon, data, index);
    index = WeaponBridge.update(entity.groundWeapon, data, index);

    entity.accelerating = data[index++] == 1;
    entity.acidSporeCount = data[index++];
...
  /**
   * Used to skip fields that are only used in initialization, but are provided each frame
   */
  public int skip(int[] data, int index) {
    index++;// Skipping 'iD'
    index++;// Skipping 'initialHitPoints'
    index += 2;// Skipping 'initialPosition'

```
As you can see the Java side has "more stuff". The reason for this is to make it easier to
map the flattened data back to the Java objects.

## How to use it
The gradle build will automatically generate the classes, no configuration is required there.

To add or change existing mappings you'll have to use annotations. Here's a list of used annotations:
`BridgeValue, Indexed, LookedUp, Named, NativeClass, Reset` - I recommend looking at existing usages
to learn more about their usage.


### BridgeValue
Used to mark a field or constructor as being transferable from C++ to Java.
It has the following attributes:
* `accessor` - used to retrieve the value on the C++ side if the name doesn't match the `getXYZ` pattern
* `indirection` - used to "magically" map a part of the flattened structure to another object. 
The getter used in C++ will be a composition of multiple names. For example, `Unit.airWeapon` is a 
`Weapon`. It's name `airWeapon` doesn't correspond to an object in C++. `Weapon`'s `type` field has
and `indirection "type"`. The resulting getter will be 
`get` + `airWeapon` (field name) + `type` (indirection) = `getAirWeaponType()`.
* `initializeOnly` - only use this field for the first time and skip when updating.

### Indexed
A field marked with this generates a C++ getter which take an index argument. This should be
used together with `BridgeValue.accessor` and will generate a variable `i`.

It has one attribute, `getAmountBy` - which is the getter used to retrieve the number of entries
that can be retrieved.

### LookedUp
Fields marked this way are actually `ids` of some sort of entity. The generated Java code will
use the `BW` class together with the configured accessor by the attribtue `method` to retrieve
the Java object.

### Named
Fields marked with this will be given a fixed index within the data structure and have a 
public field with that index in the Java code. Only fields with a constant amount of 
values in the data array can be used. (Ie. primitive values, ids, constructor calls)

### NativeClass
Classes marked with this annotation will have a C++ counterpart being generated.

It has the following attributes:
* `name` - the name of the BWAPI class represented by this class
* `parentName` - the name of the BWAPI class where this class is actually part of. 
`name` must be empty in this case.
* `accessOperator` - Either `->` or `.` depending on the C++ type, (object, enum)

### Reset
Fields marked with this will be resetted to the value given in the `value` attribute.
The reset will be performed, even if the entity will not be updated. This can be used
to update fields like `visible` even if the respective unit is out of sight and therefore
no longer updated.       
