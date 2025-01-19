package com.eaapps.headlines.data.datasource.local

import com.eaapps.database.dto.HeadlineDao
import com.eaapps.database.entities.HeadlineArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface HeadlineLocalDatasource {

    fun getTopHeadline(): Flow<List<HeadlineArticleEntity>>

    suspend fun insertTopHeadline(headlines: List<HeadlineArticleEntity>)

    suspend fun clearTopHeadline()

}


class HeadlineLocalDatasourceImpl @Inject constructor(private val dao: HeadlineDao) : HeadlineLocalDatasource {
    override fun getTopHeadline(): Flow<List<HeadlineArticleEntity>> = dao.getTopHeadline()

    override suspend fun insertTopHeadline(headlines: List<HeadlineArticleEntity>) = withContext(Dispatchers.IO) {
        dao.insertTopHeadline(headlines)
    }

    override suspend fun clearTopHeadline() = withContext(Dispatchers.IO) {
        dao.clearTopHeadline()
    }


}