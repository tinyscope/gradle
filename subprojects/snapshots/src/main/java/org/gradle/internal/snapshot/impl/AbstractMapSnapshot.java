/*
 * Copyright 2019 the original author or authors.
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

package org.gradle.internal.snapshot.impl;

import com.google.common.collect.ImmutableMap;
import org.gradle.internal.hash.Hashable;
import org.gradle.internal.hash.Hasher;

import java.util.Map;

public class AbstractMapSnapshot<T extends Hashable> implements Hashable {
    private final ImmutableMap<T, T> entries;

    public AbstractMapSnapshot(ImmutableMap<T, T> entries) {
        this.entries = entries;
    }

    public ImmutableMap<T, T> getEntries() {
        return entries;
    }

    @Override
    public void appendToHasher(Hasher hasher) {
        hasher.putString("Map");
        hasher.putInt(entries.size());
        for (Map.Entry<T, T> entry : entries.entrySet()) {
            entry.getKey().appendToHasher(hasher);
            entry.getValue().appendToHasher(hasher);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        AbstractMapSnapshot other = (AbstractMapSnapshot) obj;
        return entries.equals(other.entries);
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }
}
