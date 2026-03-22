package io.github.ryosuke37.sylva;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

import javax.lang.model.element.ExecutableElement;
import java.beans.Introspector;

public class CustomAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    public CustomAccessorNamingStrategy() {
        super();
    }

    @Override
    public boolean isSetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return (methodName.startsWith("with") || methodName.startsWith("set")) && methodName.length() > 4;
    }

    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        String[] METHOD_PREFIXES = {"get", "set", "with"};
        String propertyName = methodName;
        for (String prefix : METHOD_PREFIXES) {
            if (methodName.startsWith(prefix)) {
                propertyName = methodName.substring(prefix.length());
            }
        }
        return Introspector.decapitalize(propertyName);
    }
}