/*
 * Copyright 2015 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.tasks.persistence;

import java.util.Map;

import org.terasology.persistence.typeHandling.DeserializationContext;
import org.terasology.persistence.typeHandling.PersistedData;
import org.terasology.persistence.typeHandling.PersistedDataMap;
import org.terasology.persistence.typeHandling.RegisterTypeHandler;
import org.terasology.persistence.typeHandling.*;
import java.util.Optional;
import org.terasology.tasks.GoToBeaconTask;

import com.google.common.collect.ImmutableMap;

@RegisterTypeHandler
public class GoToBeaconTaskTypeHandler extends TypeHandler<GoToBeaconTask> {

    @Override
    public PersistedData serialize(GoToBeaconTask task, PersistenceDataSerializer context) {
        Map<String, PersistedData> data = ImmutableMap.of(
                "beaconId", context.create(task.getTargetBeaconName()));

        return context.create(ImmutableMap.of(
                "data", context.create(data)));
    }

    @Override
    public Optional<GoToBeaconTask> deserialize(PersistedData data) {
    	if (!data.isValueMap())
    	{
    		return Optional.empty();
    	}
        PersistedDataMap root = data.getAsValueMap();
        String id = root.get("id").getAsString();
        PersistedDataMap taskData = root.get("data").getAsValueMap();
        return Optional.ofNullable(new GoToBeaconTask(id,
                taskData.get("beaconId").getAsString()));
    }

}
