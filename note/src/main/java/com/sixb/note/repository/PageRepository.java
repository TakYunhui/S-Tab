package com.sixb.note.repository;

import com.sixb.note.entity.Note;
import com.sixb.note.entity.Page;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends Neo4jRepository<Page, String> {

    @Query("MATCH (p:Page) WHERE p.isDeleted = true RETURN p")
    List<Page> findDeletedPages(@Param("userId") long userId);

    @Query("MATCH (p:Page) WHERE p.pageId = $pageId RETURN p")
    Page findPageById(@Param("pageId") String pageId);

    @Query("MATCH (u:User {userId: $userId})-[:Like]->(p:Page) RETURN p")
    List<Page> findAllLikedPagesByUserId(@Param("userId") long userId);

    @Query("MATCH (u:User {userId: $userId})-[r:Like]->(p:Page {pageId: $itemId}) DELETE r")
    void deleteLikePage(@Param("userId") long userId, @Param("itemId") String itemId);

    @Query("MATCH (n: Note {noteId: $noteId})-[r:NextPage]->(p: Page) RETURN p")
    Page findFirstPageByNoteId(@Param("noteId") String noteId);

    @Query("MATCH (p: Page {pageId: $pageId})-[r:NextPage]->(p1: Page) RETURN p1")
    Page getNextPageByPageId(@Param("pageId") String pageId);

    @Query("MATCH (u:User {userId: $userId})-[r:Like]->(p:Page {pageId: $pageId}) RETURN COUNT(*) > 0 AS liked")
    boolean isLikedByPageId(@Param("userId") long userId, @Param("pageId") String pageId);

    @Query("MATCH (p: Page {pageId: $pageId})-[r:NextPage]->(p1: Page) DELETE r")
    void deleteNextPageRelation(@Param("pageId") String pageId);

    @Query("MATCH (n:Note {noteId: $noteId})-[:NextPage*]->(page:Page)\n" +
            "WHERE NOT page.isDeleted\n" +
            "RETURN collect(page) AS allPages")
    List<Page> findAllPagesByNoteId(@Param("noteId") String noteId);
}
