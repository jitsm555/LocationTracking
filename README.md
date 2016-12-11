# Tracking location with minimum battery drainage

Location Tracking System (purpose minimum battery drainage)

The Above solution help you to solve your problem of battery drainge while loation tracking.

Following are the two apis which we are using for these solution.

1. Fused Location Api

2. Activity Recognition Api.

Activity Recognition Api:-
                                 It contains list of activities that user doing right now. Activities are used based on their confidence which calculated by sensors that used in device.

Following are some Activities 

IN_VEHICLE :- The device is in a vehicle, such as car.

ON_BYCYLE :- The device is on bicycle

ON_FOOT :-  The device which kept with user while walking or running

RUNNING :- The device who is running

STILL :- The device is still (not moving)

TILTING :- The device angle relative to gravity changed significantly.

UNKNOWN :- Unable to detect the current activity.

WALKING :- The device is on a user who is walking.

Fused Location Api:- 
                                      Fused Location Provider automatically decides best location from the options available.It will select from GPS or network provider.So if the device GPS is off we can still get the location from Network provider.

please refer blog for more details.
