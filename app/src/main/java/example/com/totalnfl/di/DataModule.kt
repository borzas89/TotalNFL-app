package example.com.totalnfl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.com.totalnfl.data.remote.AdjustmentService
import example.com.totalnfl.data.remote.AdjustmentServiceImpl
import example.com.totalnfl.data.remote.PredictionService
import example.com.totalnfl.data.remote.PredictionServiceImpl

@InstallIn(SingletonComponent::class)
@Module
interface DataModule  {

    @Binds
    fun bindPredictionService(impl: PredictionServiceImpl): PredictionService

    @Binds
    fun bindAdjustmentService(impl: AdjustmentServiceImpl): AdjustmentService
}