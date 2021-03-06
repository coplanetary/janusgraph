// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.core;

import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;


public interface JanusGraphComputer extends GraphComputer {

    enum ResultMode {
        NONE, PERSIST, LOCALTX;

        public ResultGraph toResultGraph() {
            switch(this) {
                case NONE: return ResultGraph.ORIGINAL;
                case PERSIST: return ResultGraph.ORIGINAL;
                case LOCALTX: return ResultGraph.NEW;
                default: throw new AssertionError("Unrecognized option: " + this);
            }
        }

        public Persist toPersist() {
            switch(this) {
                case NONE: return Persist.NOTHING;
                case PERSIST: return Persist.VERTEX_PROPERTIES;
                case LOCALTX: return Persist.VERTEX_PROPERTIES;
                default: throw new AssertionError("Unrecognized option: " + this);
            }
        }

    }

    @Override
    JanusGraphComputer workers(int threads);

    default JanusGraphComputer resultMode(ResultMode mode) {
        result(mode.toResultGraph());
        persist(mode.toPersist());
        return this;
    }
}
