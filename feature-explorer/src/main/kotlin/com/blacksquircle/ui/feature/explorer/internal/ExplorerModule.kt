/*
 * Copyright 2022 Squircle CE contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blacksquircle.ui.feature.explorer.internal

import android.content.Context
import com.blacksquircle.ui.core.data.factory.FilesystemFactory
import com.blacksquircle.ui.core.data.storage.database.AppDatabase
import com.blacksquircle.ui.core.data.storage.keyvalue.SettingsManager
import com.blacksquircle.ui.core.domain.coroutine.DispatcherProvider
import com.blacksquircle.ui.feature.explorer.data.repository.ExplorerRepositoryImpl
import com.blacksquircle.ui.feature.explorer.domain.repository.ExplorerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ExplorerModule {

    @Provides
    @ViewModelScoped
    fun provideExplorerRepository(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider,
        settingsManager: SettingsManager,
        filesystemFactory: FilesystemFactory,
        appDatabase: AppDatabase,
    ): ExplorerRepository {
        return ExplorerRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            settingsManager = settingsManager,
            filesystemFactory = filesystemFactory,
            context = context,
            appDatabase = appDatabase,
        )
    }
}