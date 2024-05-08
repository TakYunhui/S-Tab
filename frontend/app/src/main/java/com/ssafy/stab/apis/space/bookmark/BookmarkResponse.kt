package com.ssafy.stab.apis.space.bookmark

import com.google.gson.annotations.SerializedName
import com.ssafy.stab.data.note.BackgroundColor
import com.ssafy.stab.data.note.TemplateType
import java.time.LocalDateTime

data class BookmarkListResponse(
    @SerializedName("folders") val folders: List<BookmardFolder>,
    @SerializedName("notes") val notes: List<BookmardNote>,
    @SerializedName("pages") val pages: List<BookmardPage>
)

data class BookmardFolder(
    @SerializedName("spaceTitle") val spaceTitle: String,
    @SerializedName("folderId") val folderId: String,
    @SerializedName("title") val title: String,
    @SerializedName("updatedAt") val updatedAt: LocalDateTime
)

data class BookmardNote(
    @SerializedName("spaceTitle") val spaceTitle: String,
    @SerializedName("noteId") val noteId: String,
    @SerializedName("title") val title: String,
    @SerializedName("totalPageCnt") val totalPageCnt: Int,
    @SerializedName("updatedAt") val updatedAt: LocalDateTime
)

data class BookmardPage(
    @SerializedName("pageId") val pageId: String,
    @SerializedName("color") val color: BackgroundColor,
    @SerializedName("template") val template: TemplateType,
    @SerializedName("direction") val direction: Int,
    @SerializedName("isBookmarked") val isBookmarked: Boolean,
    @SerializedName("pdfUrl") val pdfUrl: String,
    @SerializedName("pdfPage") val pdfPage: Int,
    @SerializedName("updatedAt") val updatedAt: LocalDateTime
)