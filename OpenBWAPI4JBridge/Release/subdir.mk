################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../BWTA.cpp \
../BridgeEnum.cpp \
../BridgeMap.cpp \
../InteractionHandler.cpp \
../MapDrawer.cpp \
../OpenBridgeModule.cpp \
../So.cpp \
../Unit.cpp 

OBJS += \
./BWTA.o \
./BridgeEnum.o \
./BridgeMap.o \
./InteractionHandler.o \
./MapDrawer.o \
./OpenBridgeModule.o \
./So.o \
./Unit.o 

CPP_DEPS += \
./BWTA.d \
./BridgeEnum.d \
./BridgeMap.d \
./InteractionHandler.d \
./MapDrawer.d \
./OpenBridgeModule.d \
./So.d \
./Unit.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -std=c++0x -I/home/imp/git/bwapi/bwapi/include -I"/home/imp/git/BWAPI4J/OpenBWAPI4JBridge/BWTA" -I"/home/imp/git/bwapi/bwapi/BWAPI/Source" -I"/home/imp/git/bwapi/bwapi/Util/Source" -I"/home/imp/git/bwapi/bwapi/BWAPICore" -I"/home/imp/git/bwapi/bwapi/OpenBWData" -I/dev/shm/java-8-openjdk-amd64/include -I/dev/shm/java-8-openjdk-amd64/include/linux -I"/home/imp/git/BWAPI4J/BWAPI4J/src/native/include" -O3 -Wall -c -fmessage-length=0 -fPIC -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


