package com.example.imovie.di

import dagger.Module

@Module(includes = [HomePresentationModule::class, DetailsPresentationModule::class, DomainModule::class, DataModule::class])
abstract class AppListModule