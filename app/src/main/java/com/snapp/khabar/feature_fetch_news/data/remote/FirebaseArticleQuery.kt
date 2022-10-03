package com.snapp.khabar.feature_fetch_news.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

private const val TAG = "FirebaseArticleQuery"
class FirebaseArticleQuery @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun getQueryCategory(
        query: String,
        value: String,
        applyCategoryFilter: Boolean
    ): Query {

        return if (applyCategoryFilter){
            Log.d(TAG, "Applying Filter...")
            firestore.collection("AllNews")
                .whereEqualTo(query,value)
                .orderBy("publishedAt",Query.Direction.DESCENDING)
                .limit(10.toLong())
        } else {
            Log.d(TAG, "Removing Filter...")
            firestore.collection("AllNews")
                .orderBy("publishedAt",Query.Direction.DESCENDING)
                .limit(10.toLong())

        }
    }
}