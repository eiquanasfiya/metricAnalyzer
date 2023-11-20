package com.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityDirector {

    private Map<String, Activity> activities;

    public ActivityDirector() {
        this.activities = new HashMap<>();
    }

    public void createActivities(Map<?, ?> javaYaml) {
        for (Map.Entry<?, ?> entry : javaYaml.entrySet()) {
            String key = (String) entry.getKey();
            LinkedHashMap<?, ?> value = (LinkedHashMap<?, ?>) entry.getValue();

            createActivity(key.toString(), value);
        }
    }

    private void createActivity(String activityName, LinkedHashMap<?, ?> data){

        // Initializes a new Activity Builder, creating a corresponding Activity along with an empty ArrayList for its components.
        ActivityBuilder builder = new ActivityBuilder();

        // Get Level
        String level = (String) data.get("level");

        builder = builder
            .setActivityName(activityName)
            .setLevel(level);

        // Add Components in the builder
        ArrayList arr = (ArrayList) data.get("components");
        for (Object element : arr) {
            if (element instanceof LinkedHashMap) {
                LinkedHashMap<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) element;
                switch (linkedHashMap.values().iterator().next().getClass().getSimpleName()) {
                    case "String":
                        builder.addStringComponent(linkedHashMap.keySet().toString().replace("[", "").replace("]", ""));
                        break;
                    case "Date":
                        builder.addDateComponent(linkedHashMap.keySet().toString().replace("[", "").replace("]", ""));
                        break;
                    case "Integer":
                        builder.addIntComponent(linkedHashMap.keySet().toString().replace("[", "").replace("]", ""));
                        break;
                    default:
                        // TODO: Raise Exception
                        System.out.println("This type does not exist");
                        break;
                }             
            }
        }
        
        Activity activity = builder.build();

        // Add Activity to the HashMap
        activities.put(activityName, activity);
    }

    public Map<String, Activity> getActivities() {
        return activities;
    }
}
