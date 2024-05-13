package com.sixb.note.repository;

import com.sixb.note.entity.Space;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceRepository extends Neo4jRepository<Space, String> {

    @Query("MATCH (s:Space) WHERE s.spaceId = $spaceId RETURN s")
    Space findSpaceById(@Param("spaceId") String spaceId);

    @Query("MATCH (u:User {userId: $userId})-[:Join]->(s:Space) RETURN s")
    List<Space> findSpaces(@Param("userId") long userId);

    @Query("MATCH (u:User {userId: $userId})-[:Join]->(s:Space) WHERE s.spaceId = $spaceId RETURN s")
    Space findSpaceByIdAndUserId(@Param("spaceId") String spaceId, @Param("userId") long userId);

    @Query("MATCH (s:Space {spaceId: $spaceId}) SET s.title = $newTitle RETURN s")
    void updateSpaceTitle(String spaceId, String newTitle);
}
