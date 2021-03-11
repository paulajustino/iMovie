package com.example.imovie.di

import dagger.Module

@Module(includes = [HomePresentationModule::class, HomeDomainModule::class, HomeDataModule::class])
abstract class HomeListModule