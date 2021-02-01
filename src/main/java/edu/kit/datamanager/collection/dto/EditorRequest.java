/*
 * Copyright 2018 Karlsruhe Institute of Technology.
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
package edu.kit.datamanager.collection.dto;

import edu.kit.datamanager.collection.domain.CollectionObject;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.json.simple.JSONObject;

/**
 *
 * @author chelbi
 */
@Builder
@Getter
public class EditorRequest {
    
    private JSONObject dataModel;
    private JSONObject uiForm;
    private List <CollectionObject> collections;
    private TabulatorItems[] items;
    
    @Builder.Default
    private boolean show= true;
    
    @Builder.Default
    private boolean edit= true;
    
    @Builder.Default
    private boolean delete= true;
    
    @Builder.Default
    private boolean create = true;
}
