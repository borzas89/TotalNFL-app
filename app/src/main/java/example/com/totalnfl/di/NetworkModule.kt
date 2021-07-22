package example.com.totalnfl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import example.com.totalnfl.network.OddsApiService
import example.com.totalnfl.network.TotalNflService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideTotalNflService(): TotalNflService =
        TotalNflService.create()

    @Singleton
    @Provides
    fun provideOddsApiService(): OddsApiService =
            OddsApiService.create()
}