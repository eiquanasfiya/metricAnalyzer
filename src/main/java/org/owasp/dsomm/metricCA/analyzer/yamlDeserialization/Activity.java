package org.owasp.dsomm.metricCA.analyzer.yamlDeserialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.owasp.dsomm.metricCA.analyzer.yamlDeserialization.components.DateComponent;
import org.owasp.dsomm.metricCA.analyzer.yamlDeserialization.components.DatePeriodComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Activity {
    private static final Logger logger = LoggerFactory.getLogger(Activity.class);

    private String level;
    private String activityName;


    @JsonIgnore
    private Map<String, Object> skeleton;
    private ArrayList<Map<String, Object>> content;

    public Activity() {
        skeleton = new HashMap<>();
        content = new ArrayList<>();
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void addComponentToSkeleton(Component component, ArrayList<String> nester) {
        if (nester.isEmpty()) {
            skeleton.put(component.getName(), component);
        } else {
            for (String key : nester) {
                if (skeleton.get(nester.get(0)) != null){ // Is key in components
                    Map<String, Object> temp = (HashMap) skeleton.get(key);
                    temp.put(component.getName(), component);
                }
                else {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put(component.getName(), component);
                    skeleton.put(key, temp);
                }
            }
        }
    }

    public Map<String, Object> getSkeleton() {
        return this.skeleton;
    }

    // Clones components from the 'skeleton' map and adds them to the 'content' ArrayList.
    // Components are added either directly or within nested HashMaps, preserving the original structure.
    public void cloneSkeletonAndAddToContent() {
        HashMap finalAcc = new HashMap<>();
        for (String componentKey : skeleton.keySet()){
            if (skeleton.get(componentKey) instanceof Component){
                try {
                    Component component = (Component) skeleton.get(componentKey);
                    finalAcc.put(componentKey, component.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (skeleton.get(componentKey) instanceof HashMap) {
                HashMap temp = new HashMap<>();
                HashMap fin = (HashMap) skeleton.get(componentKey);
                for (Object k : fin.keySet()){
                    Component component = (Component) fin.get(k);
                    try {
                        temp.put(k, component.clone());
                    } catch (CloneNotSupportedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                }
                finalAcc.put(componentKey, temp);
            }
            else {
                // TODO Raise an exception
                System.out.println("This instance should not be here!");
            }
        }
        content.add(finalAcc);
    }

    public ArrayList<Map<String, Object>> getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + activityName + ", level=" + level + "]";
    }

    public DatePeriodComponent getDatePeriodOrEndComponent() {
        for (Map<String, Object> componentMap : content) {
            for (Object component : componentMap.values()) {
                if (component instanceof DatePeriodComponent) {
                    return (DatePeriodComponent) component;
                }
            }
        }
        return null;
    }
    public Collection<DateComponent> getDateComponents() {
        Collection<DateComponent> dateComponents = new ArrayList<>();
        for (Map<String, Object> componentMap : content) {
            for (Object component : componentMap.values()) {
                if (component instanceof DateComponent) {
                    dateComponents.add((DateComponent) component);
                }
            }
        }
        return dateComponents;
    }
}
