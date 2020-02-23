package com.github.hadasbro.supplier.event;

import com.github.hadasbro.supplier.annotation.CascadePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

@SuppressWarnings("unused")
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * FieldCallback
     */
    private static class FieldCallback implements ReflectionUtils.FieldCallback {

        private boolean idFound;

        public void doWith(Field field) throws IllegalArgumentException {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        @SuppressWarnings("WeakerAccess")
        public boolean isIdFound() {
            return idFound;
        }
    }

    /**
     * onBeforeConvert
     *
     * @param event -
     */
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Object source = event.getSource();

        ReflectionUtils.doWithFields(source.getClass(), field -> {

            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadePersist.class)){
                final Object fieldValue = field.get(source);

                if(fieldValue instanceof List<?>){
                    for (Object item : (List<?>)fieldValue){
                        addItem(item);
                    }
                }else{
                    addItem(fieldValue);
                }

            }
        });
    }

    /**
     * addItem
     *
     * @param fieldValue -
     */
    private void addItem(Object fieldValue) {

        FieldCallback callback = new FieldCallback();

        ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

        if (!callback.isIdFound()){
            try {
                throw new Exception("No @Id on child element.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mongoOperations.save(fieldValue);
    }
}