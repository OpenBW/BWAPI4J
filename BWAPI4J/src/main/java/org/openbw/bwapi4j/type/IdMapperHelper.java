package org.openbw.bwapi4j.type;

import java.lang.reflect.Array;
import java.util.stream.Stream;

final class IdMapperHelper {

  private IdMapperHelper() {}

  static <E extends WithId> E[] toIdTypeArray(Class<E> enumClass) {
    E[] enumConstants = enumClass.getEnumConstants();
    int maxId = Stream.of(enumConstants).mapToInt(E::getID).max().getAsInt();
    E[] idToType = (E[]) Array.newInstance(enumClass, maxId + 1);
    for (E type : enumConstants) {
      idToType[type.getID()] = type;
    }
    return idToType;
  }
}

interface WithId {

  int getID();
}
