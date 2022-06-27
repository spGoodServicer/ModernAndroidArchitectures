package com.nereus.craftbeer.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.nereus.craftbeer.database.entity.SaleLogWithSaleLogDetail

/**
 * Sale log list dao
 *
 * @constructor  Sale log list dao
 */
@Dao
interface SaleLogListDao {
    @Transaction
    @Query("SELECT * FROM sale_logs")
    fun getSaleLogWithDetails(): List<SaleLogWithSaleLogDetail>
}