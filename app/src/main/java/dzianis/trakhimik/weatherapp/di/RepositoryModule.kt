package dzianis.trakhimik.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dzianis.trakhimik.weatherapp.data.location.DefaultLocationTracker
import dzianis.trakhimik.weatherapp.data.repository.WeatherRepositoryImpl
import dzianis.trakhimik.weatherapp.domain.location.LocationTracker
import dzianis.trakhimik.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}