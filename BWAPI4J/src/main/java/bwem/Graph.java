package bwem;

import bwem.area.Area;
import bwem.area.AreaId;
import bwem.map.MapImpl;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.util.BwemExt;
import bwem.util.Utils;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Graph
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public class Graph {

    private final MapImpl m_pMap;
    private List<Area> m_Areas;
    private List<ChokePoint> m_ChokePointList;
    private List<List<List<ChokePoint>>> m_ChokePointsMatrix; // index == Area::id x Area::id
    private List<List<Integer>> m_ChokePointDistanceMatrix; // index == ChokePoint::index x ChokePoint::index
    private List<List<CPPath>> m_PathsBetweenChokePoints; // index == ChokePoint::index x ChokePoint::index
    private CPPath m_EmptyPath;
    private int m_baseCount;

    public Graph(MapImpl pMap) {
        m_pMap = pMap;
    }

    public MapImpl GetMap() {
        return m_pMap;
    }

    public List<Area> Areas() {
        return m_Areas;
    }

    public int AreasCount() {
        return m_Areas.size();
    }

    public Area GetArea(AreaId id) {
//        bwem_assert(Valid(id));
        if (!(Valid(id))) {
            throw new IllegalArgumentException();
        }
        return m_Areas.get(id.intValue() - 1);
    }

    public Area GetArea(WalkPosition w) {
        AreaId id = GetMap().GetMiniTile(w).AreaId();
        return (id.intValue() > 0)
                ? GetArea(id)
                : null;
    }

    public Area GetArea(TilePosition w) {
        AreaId id = GetMap().GetTile(w).AreaId();
        return (id.intValue() > 0)
                ? GetArea(id)
                : null;
    }

    public Area GetNearestArea(WalkPosition p) {
        Area area = GetArea(p);
        if (area != null) {
            return area;
        }

        p = GetMap().BreadthFirstSearch(
            p,
            new Pred() { // findCond
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        MiniTile miniTile = (MiniTile) ttile;
                        return (miniTile.AreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            },
            new Pred() { // visitCond
                @Override
                public boolean is(Object... args) {
                    return true;
                }
            }
        );

        return GetArea(p);
    }

    public Area GetNearestArea(TilePosition p) {
        Area area = GetArea(p);
        if (area != null) {
            return area;
        }

        p = GetMap().BreadthFirstSearch(
            p,
            new Pred() { // findCond
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return (tile.AreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            },
            new Pred() { // visitCond
                @Override
                public boolean is(Object... args) {
                    return true;
                }
            }
        );

        return GetArea(p);
    }

    // Returns the list of all the ChokePoints in the Map.
    public List<ChokePoint> ChokePoints() {
        return m_ChokePointList;
    }

    // Returns the ChokePoints between two Areas.
    public List<ChokePoint> GetChokePoints(AreaId a, AreaId b) {
        if (!Valid(a)) {
//            bwem_assert(Valid(a));
            throw new IllegalArgumentException("failed assert: Valid(a)");
        } else if (!Valid(b)) {
//            bwem_assert(Valid(b));
            throw new IllegalArgumentException("failed assert: Valid(b)");
        } else if (!(a.intValue() != b.intValue())) {
//            bwem_assert(a != b);
            throw new IllegalArgumentException("failed assert: a.intValue() != b.intValue()");
        }

        int a_id = a.intValue();
        int b_id = b.intValue();
        if (a.intValue() > b.intValue()) {
            int tmp = a_id;
            a_id = b_id;
            b_id = tmp;
        }

        return m_ChokePointsMatrix.get(b_id).get(a_id);
    }

    // Returns the ChokePoints between two Areas.
    public List<ChokePoint> GetChokePoints(Area a, Area b) {
        return GetChokePoints(a.Id(), b.Id());
    }

//	// Returns the ground distance in pixels between cpA->Center() and cpB>Center()
	public int Distance(ChokePoint cpA, ChokePoint cpB) {
        return m_ChokePointDistanceMatrix.get(cpA.Index().intValue()).get(cpB.Index().intValue());
    }

    // Returns a list of ChokePoints, which is intended to be the shortest walking path from cpA to cpB.
	public CPPath GetPath(ChokePoint cpA, ChokePoint cpB) {
        return m_PathsBetweenChokePoints.get(cpA.Index().intValue()).get(cpB.Index().intValue());
    }

    public CPPath GetPath(Position a, Position b, MutableInt pLength) {
        Area areaA = GetNearestArea(a.toWalkPosition());
        Area areaB = GetNearestArea(b.toWalkPosition());

        if (areaA.equals(areaB)) {
            if (pLength != null) {
                pLength.setValue((int) a.getDistance(b));
            }
            return new CPPath(); //TODO: Return empty list or null?
        }

        if (!areaA.AccessibleFrom(areaB)) {
            if (pLength != null) {
                pLength.setValue(-1);
            }
            return new CPPath(); //TODO: Return empty list or null?
        }

        int minDist_A_B = Integer.MAX_VALUE;

        ChokePoint bestCpA = null;
        ChokePoint bestCpB = null;

        for (ChokePoint cpA : areaA.ChokePoints()) {
            if (!cpA.Blocked()) {
                int dist_A_cpA = (int) a.getDistance(cpA.Center().toPosition());
                for (ChokePoint cpB : areaB.ChokePoints()) {
                    if (!cpB.Blocked()) {
                        int dist_B_cpB = (int) b.getDistance(cpB.Center().toPosition());
                        int dist_A_B = dist_A_cpA + dist_B_cpB + Distance(cpA, cpB);
                        if (dist_A_B < minDist_A_B) {
                            minDist_A_B = dist_A_B;
                            bestCpA = cpA;
                            bestCpB = cpB;
                        }
                    }
                }
            }
        }

//        bwem_assert(minDist_A_B != numeric_limits<int>::max());
        if (!(minDist_A_B != Integer.MAX_VALUE)) {
            throw new IllegalStateException("failed assert: minDist_A_B != Integer.MAX_VALUE");
        }

        CPPath path = GetPath(bestCpA, bestCpB);

        if (pLength != null) {
//            bwem_assert(Path.size() >= 1);
            if (!(path.size() >= 1)) {
                throw new IllegalStateException("failed assert: path.size() >= 1");
            }

            pLength.setValue(minDist_A_B);

            if (path.size() == 1) {
//                bwem_assert(pBestCpA == pBestCpB);
                if (!(bestCpA.equals(bestCpB))) {
                    throw new IllegalStateException("failed assert: bestCpA.equals(bestCpB)");
                }
                ChokePoint cp = bestCpA;

                Position cpEnd1 = BwemExt.center(cp.Pos(ChokePoint.Node.end1));
                Position cpEnd2 = BwemExt.center(cp.Pos(ChokePoint.Node.end2));
                if (Utils.intersect(a.getX(), a.getY(), b.getX(), b.getY(), cpEnd1.getX(), cpEnd1.getY(), cpEnd2.getX(), cpEnd2.getY())) {
                    pLength.setValue(a.getDistance(b));
                } else {
                    ChokePoint.Node[] nodes = {ChokePoint.Node.end1, ChokePoint.Node.end2};
                    for (ChokePoint.Node node : nodes) {
                        Position c = BwemExt.center(cp.Pos(node));
                        int dist_A_B = (int) (a.getDistance(c) + b.getDistance(c));
                        if (dist_A_B < pLength.intValue()) {
                            pLength.setValue(dist_A_B);
                        }
                    }
                }
            }
        }

        return GetPath(bestCpA, bestCpB);
    }

	public CPPath GetPath(Position a, Position b) {
        return GetPath(a, b, null);
    }

	public int BaseCount() {
        return m_baseCount;
    }

//	// Creates a new Area for each pair (top, miniTiles) in AreasList (See Area::Top() and Area::MiniTiles())
//	void								CreateAreas(const vector<pair<BWAPI::WalkPosition, int>> & AreasList);
//    void Graph::CreateAreas(const vector<pair<WalkPosition, int>> & AreasList)
//    {
//        m_Areas.reserve(AreasList.size());
//        for (Area::id id = 1 ; id <= (Area::id)AreasList.size() ; ++id)
//        {
//            WalkPosition top = AreasList[id-1].first;
//            int miniTiles = AreasList[id-1].second;
//            m_Areas.emplace_back(this, id, top, miniTiles);
//        }
//    }

//
//	// Creates a new Area for each pair (top, miniTiles) in AreasList (See Area::Top() and Area::MiniTiles())
//	void								CreateChokePoints();
//void Graph::CreateChokePoints()
//{
//	ChokePoint::index newIndex = 0;
//
//	vector<Neutral *> BlockingNeutrals;
//	for (auto & s : GetMap()->StaticBuildings())		if (s->Blocking()) BlockingNeutrals.push_back(s.get());
//	for (auto & m : GetMap()->Minerals())			if (m->Blocking()) BlockingNeutrals.push_back(m.get());
//
//	const int pseudoChokePointsToCreate = count_if(BlockingNeutrals.begin(), BlockingNeutrals.end(),
//											[](const Neutral * n){ return !n->NextStacked(); });
//
//	// 1) Size the matrix
//	m_ChokePointsMatrix.resize(AreasCount() + 1);
//	for (Area::id id = 1 ; id <= AreasCount() ; ++id)
//		m_ChokePointsMatrix[id].resize(id);			// triangular matrix
//
//	// 2) Dispatch the global raw frontier between all the relevant pairs of Areas:
//	map<pair<Area::id, Area::id>, vector<WalkPosition>> RawFrontierByAreaPair;
//	for (const auto & raw : GetMap()->RawFrontier())
//	{
//		Area::id a = raw.first.first;
//		Area::id b = raw.first.second;
//		if (a > b) swap(a, b);
//		bwem_assert(a <= b);
//		bwem_assert((a >= 1) && (b <= AreasCount()));
//
//		RawFrontierByAreaPair[make_pair(a, b)].push_back(raw.second);
//	}
//
//	// 3) For each pair of Areas (A, B):
//	for (auto & raw : RawFrontierByAreaPair)
//	{
//		Area::id a = raw.first.first;
//		Area::id b = raw.first.second;
//
//		const vector<WalkPosition> & RawFrontierAB = raw.second;
//
//		// Because our dispatching preserved order,
//		// and because Map::m_RawFrontier was populated in descending order of the altitude (see Map::ComputeAreas),
//		// we know that RawFrontierAB is also ordered the same way, but let's check it:
//		{
//			vector<altitude_t> Altitudes;
//			for (auto w : RawFrontierAB)
//				Altitudes.push_back(GetMap()->GetMiniTile(w).Altitude());
//
//			bwem_assert(is_sorted(Altitudes.rbegin(), Altitudes.rend()));
//		}
//
//		// 3.1) Use that information to efficiently cluster RawFrontierAB in one or several chokepoints.
//		//    Each cluster will be populated starting with the center of a chokepoint (max altitude)
//		//    and finishing with the ends (min altitude).
//		const int cluster_min_dist = (int)sqrt(lake_max_miniTiles);
//		vector<deque<WalkPosition>> Clusters;
//		for (auto w : RawFrontierAB)
//		{
//			bool added = false;
//			for (auto & Cluster : Clusters)
//			{
//				int distToFront = queenWiseDist(Cluster.front(), w);
//				int distToBack = queenWiseDist(Cluster.back(), w);
//				if (min(distToFront, distToBack) <= cluster_min_dist)
//				{
//					if (distToFront < distToBack)	Cluster.push_front(w);
//					else							Cluster.push_back(w);
//
//					added = true;
//					break;
//				}
//			}
//
//			if (!added) Clusters.push_back(deque<WalkPosition>(1, w));
//		}
//
//		// 3.2) Create one Chokepoint for each cluster:
//		GetChokePoints(a, b).reserve(Clusters.size() + pseudoChokePointsToCreate);
//		for (const auto & Cluster : Clusters)
//			GetChokePoints(a, b).emplace_back(this, newIndex++, GetArea(a), GetArea(b), Cluster);
//	}
//
//	// 4) Create one Chokepoint for each pair of blocked areas, for each blocking Neutral:
//
//
//	for (Neutral * pNeutral : BlockingNeutrals)
//		if (!pNeutral->NextStacked())		// in the case where several neutrals are stacked, we only consider the top
//		{
//			vector<const Area *> BlockedAreas = pNeutral->BlockedAreas();
//			for (const Area * pA : BlockedAreas)
//			for (const Area * pB : BlockedAreas)
//			{
//				if (pB == pA) break;	// breaks symmetry
//
//				auto center = GetMap()->BreadthFirstSearch(WalkPosition(pNeutral->Pos()),
//						[](const MiniTile & miniTile, WalkPosition) { return miniTile.Walkable(); },	// findCond
//						[](const MiniTile &,          WalkPosition) { return true; });					// visitCond
//
//				GetChokePoints(pA, pB).reserve(pseudoChokePointsToCreate);
//				GetChokePoints(pA, pB).emplace_back(this, newIndex++, pA, pB, deque<WalkPosition>(1, center), pNeutral);
//			}
//		}
//
//	// 5) Set the references to the freshly created Chokepoints:
//	for (Area::id a = 1 ; a <= AreasCount() ; ++a)
//	for (Area::id b = 1 ; b < a ; ++b)
//		if (!GetChokePoints(a, b).empty())
//		{
//			GetArea(a)->AddChokePoints(GetArea(b), &GetChokePoints(a, b));
//			GetArea(b)->AddChokePoints(GetArea(a), &GetChokePoints(a, b));
//
//			for (auto & cp : GetChokePoints(a, b))
//				m_ChokePointList.push_back(&cp);
//		}
//}
//
//	void								ComputeChokePointDistanceMatrix();
//// Computes the ground distances between any pair of ChokePoints in pContext
//// This is achieved by invoking several times pContext->ComputeDistances,
//// which effectively computes the distances from one starting ChokePoint, using Dijkstra's algorithm.
//// If Context == Area, Dijkstra's algorithm works on the Tiles inside one Area.
//// If Context == Graph, Dijkstra's algorithm works on the GetChokePoints between the AreaS.
//template<class Context>
//void Graph::ComputeChokePointDistances(const Context * pContext)
//{
/////	multimap<int, vector<WalkPosition>> trace;
//
//	for (const ChokePoint * pStart : pContext->ChokePoints())
//	{
//		vector<const ChokePoint *> Targets;
//		for (const ChokePoint * cp : pContext->ChokePoints())
//		{
//			if (cp == pStart) break;	// breaks symmetry
//			Targets.push_back(cp);
//		}
//
//		auto DistanceToTargets = pContext->ComputeDistances(pStart, Targets);
//
//		for (int i = 0 ; i < (int)Targets.size() ; ++i)
//		{
//			int newDist = DistanceToTargets[i];
//			int existingDist = Distance(pStart, Targets[i]);
//
//			if (newDist && ((existingDist == -1) || (newDist < existingDist)))
//			{
//				SetDistance(pStart, Targets[i], newDist);
//
//				// Build the path from pStart to Targets[i]:
//
//				CPPath Path {pStart, Targets[i]};
//
//				// if (Context == Graph), there may be intermediate ChokePoints. They have been set by ComputeDistances,
//				// so we just have to collect them (in the reverse order) and insert them into Path:
//				if ((void *)(pContext) == (void *)(this))	// tests (Context == Graph) without warning about constant condition
//					for (const ChokePoint * pPrev = Targets[i]->PathBackTrace() ; pPrev != pStart ; pPrev = pPrev->PathBackTrace())
//						Path.insert(Path.begin()+1, pPrev);
//
//				SetPath(pStart, Targets[i], Path);
//
//			///	vector<WalkPosition> PathTrace;
//			///	for (auto e : Path) PathTrace.push_back(e->Center());
//			///	trace.emplace(int(0.5 + DistanceToTargets[i]/8.0), PathTrace);
//			}
//		}
//	}
//
/////	for (auto & line : trace) { Log << line.first; for (auto e : line.second) Log << " " << e; Log << endl; }
//
//}


//	void								CollectInformation();

    public void CreateBases()
    {
        m_baseCount = 0;
        for (Area area : m_Areas) {
            area.CreateBases();
            m_baseCount += area.Bases().size();
        }
    }

//
//private:
//	template<class Context>

//	void								ComputeChokePointDistances(const Context * pContext);
//void Graph::ComputeChokePointDistanceMatrix()
//{
//	// 1) Size the matrix
//	m_ChokePointDistanceMatrix.clear();
//	m_ChokePointDistanceMatrix.resize(m_ChokePointList.size());
//	for (auto & line : m_ChokePointDistanceMatrix)
//		line.resize(m_ChokePointList.size(), -1);
//
//	m_PathsBetweenChokePoints.clear();
//	m_PathsBetweenChokePoints.resize(m_ChokePointList.size());
//	for (auto & line : m_PathsBetweenChokePoints)
//		line.resize(m_ChokePointList.size());
//
//	// 2) Compute distances inside each Area
//	for (const Area & area : Areas())
//		ComputeChokePointDistances(&area);
//
//	// 3) Compute distances through connected Areas
//	ComputeChokePointDistances(this);
//
//	for (const ChokePoint * cp : ChokePoints())
//	{
//		SetDistance(cp, cp, 0);
//		SetPath(cp, cp, CPPath{cp});
//	}
//
//	// 4) Update Area::m_AccessibleNeighbours for each Area
//	for (Area & area : Areas())
//		area.UpdateAccessibleNeighbours();
//
//	// 5)  Update Area::m_groupId for each Area
//	UpdateGroupIds();
//}

//	vector<int>							ComputeDistances(const ChokePoint * pStartCP, const vector<const ChokePoint *> & TargetCPs) const;
// Returns Distances such that Distances[i] == ground_distance(start, Targets[i]) in pixels
// Any Distances[i] may be 0 (meaning Targets[i] is not reachable).
// This may occur in the case where start and Targets[i] leave in different continents or due to Bloqued intermediate ChokePoint(s).
// For each reached target, the shortest path can be derived using
// the backward trace set in cp->PathBackTrace() for each intermediate ChokePoint cp from the target.
// Note: same algo than Area::ComputeDistances (derived from Dijkstra)
//vector<int> Graph::ComputeDistances(const ChokePoint * start, const vector<const ChokePoint *> & Targets) const
//{
//	const MapImpl * pMap = GetMap();
//	vector<int> Distances(Targets.size());
//
//	Tile::UnmarkAll();
//
//	multimap<int, const ChokePoint *> ToVisit;	// a priority queue holding the GetChokePoints to visit ordered by their distance to start.
//	ToVisit.emplace(0, start);
//
//	int remainingTargets = Targets.size();
//	while (!ToVisit.empty())
//	{
//		int currentDist = ToVisit.begin()->first;
//		const ChokePoint * current = ToVisit.begin()->second;
//		const Tile & currentTile = pMap->GetTile(TilePosition(current->Center()), check_t::no_check);
//		bwem_assert(currentTile.InternalData() == currentDist);
//		ToVisit.erase(ToVisit.begin());
//		currentTile.SetInternalData(0);										// resets Tile::m_internalData for future usage
//		currentTile.SetMarked();
//
//		for (int i = 0 ; i < (int)Targets.size() ; ++i)
//			if (current == Targets[i])
//			{
//				Distances[i] = currentDist;
//				--remainingTargets;
//			}
//		if (!remainingTargets) break;
//
//		if (current->Blocked() && (current != start)) continue;
//
//		for (const Area * pArea : {current->GetAreas().first, current->GetAreas().second})
//			for (const ChokePoint * next : pArea->ChokePoints())
//				if (next != current)
//				{
//					const int newNextDist = currentDist + Distance(current, next);
//					const Tile & nextTile = pMap->GetTile(TilePosition(next->Center()), check_t::no_check);
//					if (!nextTile.Marked())
//					{
//						if (nextTile.InternalData())	// next already in ToVisit
//						{
//							if (newNextDist < nextTile.InternalData())		// nextNewDist < nextOldDist
//							{	// To update next's distance, we need to remove-insert it from ToVisit:
//								auto range = ToVisit.equal_range(nextTile.InternalData());
//								auto iNext = find_if(range.first, range.second, [next]
//									(const pair<int, const ChokePoint *> & e) { return e.second == next; });
//								bwem_assert(iNext != range.second);
//
//								ToVisit.erase(iNext);
//								nextTile.SetInternalData(newNextDist);
//								next->SetPathBackTrace(current);
//								ToVisit.emplace(newNextDist, next);
//							}
//						}
//						else
//						{
//							nextTile.SetInternalData(newNextDist);
//							next->SetPathBackTrace(current);
//							ToVisit.emplace(newNextDist, next);
//						}
//					}
//				}
//	}
//
////	bwem_assert(!remainingTargets);
//
//	// Reset Tile::m_internalData for future usage
//	for (auto e : ToVisit)
//		pMap->GetTile(TilePosition(e.second->Center()), check_t::no_check).SetInternalData(0);
//
//	return Distances;
//}

//	void								SetDistance(const ChokePoint * cpA, const ChokePoint * cpB, int value);
//    void Graph::SetDistance(const ChokePoint * cpA, const ChokePoint * cpB, int value)
//    {
//        m_ChokePointDistanceMatrix[cpA->Index()][cpB->Index()] =
//        m_ChokePointDistanceMatrix[cpB->Index()][cpA->Index()] = value;
//    }

//    void Graph::UpdateGroupIds()
//    {
//    	Area::groupId nextGroupId = 1;
//
//    	Area::UnmarkAll();
//    	for (Area & start : Areas())
//    		if (!start.Marked())
//    		{
//    			vector<Area *> ToVisit{&start};
//    			while (!ToVisit.empty())
//    			{
//    				Area * current = ToVisit.back();
//    				ToVisit.pop_back();
//    				current->SetGroupId(nextGroupId);
//
//    				for (const Area * next : current->AccessibleNeighbours())
//    					if (!next->Marked())
//    					{
//    						next->SetMarked();
//    						ToVisit.push_back(const_cast<Area *>(next));
//    					}
//    			}
//    			++nextGroupId;
//    		}
//    }

//	void								SetPath(const ChokePoint * cpA, const ChokePoint * cpB, const CPPath & PathAB);
//    void Graph::SetPath(const ChokePoint * cpA, const ChokePoint * cpB, const CPPath & PathAB)
//    {
//        m_PathsBetweenChokePoints[cpA->Index()][cpB->Index()] = PathAB;
//        m_PathsBetweenChokePoints[cpB->Index()][cpA->Index()].assign(PathAB.rbegin(), PathAB.rend());
//    }

    private boolean Valid(AreaId id) {
        return (id.intValue() >= 1 && id.intValue() <= AreasCount());
    }

//Area * mainArea(MapImpl * pMap, TilePosition topLeft, TilePosition size)
//{
//	map<Area *, int> map_Area_freq;
//
//	for (int dy = 0 ; dy < size.y ; ++dy)
//	for (int dx = 0 ; dx < size.x ; ++dx)
//		if (Area * area = pMap->GetArea(topLeft + TilePosition(dx, dy)))
//			++map_Area_freq[area];
//
//	return map_Area_freq.empty() ? nullptr : map_Area_freq.rbegin()->first;
//}

}
