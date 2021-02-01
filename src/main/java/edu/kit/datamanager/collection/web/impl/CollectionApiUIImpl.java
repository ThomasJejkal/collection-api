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
package edu.kit.datamanager.collection.web.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.datamanager.collection.dao.ICollectionObjectDao;
import edu.kit.datamanager.collection.domain.CollectionObject;
import edu.kit.datamanager.collection.dto.EditorRequest;
import edu.kit.datamanager.collection.dto.TabulatorItems;
import edu.kit.datamanager.collection.web.CollectionApiUI;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author chelbi
 */
@Controller
public class CollectionApiUIImpl implements CollectionApiUI {

    private final static String DATAMODEL = "/static/jsonSchemas/dataModel.json";
    private final static String UIFORM = "/static/jsonSchemas/uiForm.json";
    private final static String ITEMS = "/static/jsonSchemas/items.json";

    @Autowired
    private ICollectionObjectDao collectionDao;

    @RequestMapping("/collections")
    @Override
    public ModelAndView collections() {
        List<CollectionObject> collections = collectionDao.findAll();

        EditorRequest request = EditorRequest.builder()
                .dataModel(getJsonObject(DATAMODEL))
                .uiForm(getJsonObject(UIFORM))
                .collections(collections)
                .items(getJsonArrayOfItems(ITEMS)).build();

        ModelAndView model = new ModelAndView("collections");
        model.addObject("request", request);
        return model;
    }

    private JSONObject getJsonObject(String path) {
        Resource resource = new ClassPathResource(path);
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(
                    new InputStreamReader(resource.getInputStream(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private TabulatorItems[] getJsonArrayOfItems(String path) {
        ObjectMapper mapper = new ObjectMapper();
        TabulatorItems[] items = null;
        Resource resource = new ClassPathResource(path);
        try {
            items = mapper.readValue(Files.newBufferedReader(Paths.get(resource.getURI()), StandardCharsets.UTF_8), TabulatorItems[].class);
        } catch (IOException ex) {
            Logger.getLogger(CollectionApiUIImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

}
