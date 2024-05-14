package com.ssafy.stab.apis.space.bookmark

import android.util.Log
import com.ssafy.stab.apis.RetrofitClient
import com.ssafy.stab.apis.space.folder.Folder
import com.ssafy.stab.apis.space.folder.Note
import com.ssafy.stab.data.PreferencesUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)
private val accessToken = PreferencesUtil.getLoginDetails().accessToken
private val authorizationHeader = "Bearer $accessToken"
fun getBookMarkList(onFolderResult: (List<BookmardFolder>?) -> Unit, onNoteResult: (List<BookmardNote>?) -> Unit, onPageResult: (List<BookmardPage>?) -> Unit) {
    val apiService = RetrofitClient.instance.create(ApiService::class.java)
    val call = apiService.getBookmarkList(authorizationHeader)

    call.enqueue(object: Callback<BookmarkListResponse> {
        override fun onResponse(call: Call<BookmarkListResponse>, response: Response<BookmarkListResponse>) {
            if (response.isSuccessful) {
                Log.d("APIResponse", response.body().toString())
                val fileListResponse = response.body()
                onFolderResult(fileListResponse?.folders)
                onNoteResult(fileListResponse?.notes)
                onPageResult(fileListResponse?.pages)
            } else {
                println("Response not successful: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<BookmarkListResponse>, t: Throwable) {
            Log.d("APIResponse", "요청 실패")
        }
    })
}

fun addBookMark(id: String) {
    val addBookmarkRequest = AddBookmarkRequest(id)
    val call = apiService.addBookmark(authorizationHeader, addBookmarkRequest)

    call.enqueue(object: Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Log.d("APIResponse", "요청 성공")
            } else {
                println("Response not successful: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.d("APIResponse", "요청 실패")
        }
    })

}

fun deleteBookMark(fileId: String) {
    val call = apiService.deleteBookmark(authorizationHeader, fileId)

    call.enqueue(object: Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Log.d("APIResponse", "요청 성공")
            } else {
                println("Response not successful: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.d("APIResponse", "요청 실패")
        }
    })
}