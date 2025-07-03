package com.buyzon.core.models;

import com.buyzon.core.services.impl.CountryReaderConsumer;
import com.buyzon.core.services.impl.TeamMemberService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "buyzon/components/teammembers"
)
@Exporter(name="jackson", extensions = "json",
options={@ExporterOption(name="SerializationFeature.WRAP_ROOT_VALUE",value="true")})
@JsonRootName("rootNode")
public class TeamMembersModel {
    @OSGiService
    private CountryReaderConsumer consumer;

    public List<String> getAllCountryDetails() {
        return consumer.getAllConfigDetails();
    }
    @OSGiService
    TeamMemberService teamMemberService;

    @JsonProperty(value="Config Message")
    private String message;

    // ✅ Corrected to use ChildResource since we're adapting from Resource
    @ChildResource
    private List<TeamMember> teamMembers;

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    // Inner model for individual team members
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class TeamMember {

        @ValueMapValue
        private String name;

        @ValueMapValue
        private String role;

        @ChildResource
        private List<SocialLink> socialLinks;

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public List<SocialLink> getSocialLinks() {
            return socialLinks;
        }
    }

    // Inner model for social links under each team member
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class SocialLink {

        @ValueMapValue
        private String platform;

        @ValueMapValue
        private String url;

        public String getPlatform() {
            return platform;
        }

        public String getUrl() {
            return url;
        }
    }

    @PostConstruct
    protected void init(){
        if(teamMemberService!=null){
            message = teamMemberService.getMessage();
        }
    }

    public String getMessage() {
        return message;
    }
}
