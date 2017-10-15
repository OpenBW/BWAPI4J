package bwem;

import java.util.List;

/*
Note: This code was originally part of "neutral.h" and documented as only
applicable to Mineral and StaticBuilding objects.
    neutral.h:83:
    // Tells whether this Neutral is blocking some ChokePoint.
    // This applies to Minerals and StaticBuildings only.
    // For each blocking Neutral, a pseudo ChokePoint (which is Blocked()) is created on top of it,
    // with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is created.
    // Cf. definition of pseudo ChokePoints in class ChokePoint comment.
    // Cf. ChokePoint::BlockingNeutral and ChokePoint::Blocked.
*/
public interface IBlocking {

    boolean isBlocking();

    List<Area> getBlockingAreas();

}
