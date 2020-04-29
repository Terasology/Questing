/*
 * Copyright 2015 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.tasks;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.logic.inventory.StartingInventoryComponent;

import java.util.List;

/**
 *
 */
public class DefaultQuest implements Quest {
    private final String shortName;
    private final String description;
    private final TaskGraph tasks;
    private final EntityRef entity;
    private final List<StartingInventoryComponent.InventoryItem> reward;

    public DefaultQuest(EntityRef entity, String shortName, String description, TaskGraph tasks, List<StartingInventoryComponent.InventoryItem> reward) {
        this.entity = entity;
        this.shortName = shortName;
        this.description = description;
        this.tasks = tasks;
        this.reward = reward;
    }

    @Override
    public EntityRef getEntity() {
        return entity;
    }

    @Override
    public String getShortName() {
        return shortName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public TaskGraph getTaskGraph() {
        return tasks;
    }

    @Override
    public List<StartingInventoryComponent.InventoryItem> getReward() {return reward;}

    /**
     * The quest fails if any task failed. Otherwise, the quest is active if any task is active. Otherwise,
     * the quest has completed successfully.
     * @return the quest status
     */
    @Override
    public Status getStatus() {
        for (Task task : tasks) {
            Status taskStatus = tasks.getTaskStatus(task);
            if (taskStatus == Status.FAILED) {
                return Status.FAILED;
            }
            if (taskStatus == Status.ACTIVE) {
                return Status.ACTIVE;
            }
        }
        return Status.SUCCEEDED;
    }

    @Override
    public String toString() {
        return String.format("DefaultQuest [%s]", shortName);
    }
}
