package com.buyzon.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Team Configuration", description = "Configuration for Team member component")
public @interface TeamMemberDetails {
    @AttributeDefinition(name = "Message")
    String message() default "This is default message";
}
