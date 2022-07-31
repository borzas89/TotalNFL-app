package example.com.totalnfl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.com.totalnfl.network.TotalNflApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideTotalNflService(): TotalNflApi =
        TotalNflApi.create()
}