package ma.emsi.myplatform.authentication.auth;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate-account");

    private final String name;

    EmailTemplateName(String name) {
        this.name=name;
    }
}
