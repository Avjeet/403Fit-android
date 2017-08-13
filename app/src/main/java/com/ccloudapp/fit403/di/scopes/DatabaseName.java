package com.ccloudapp.fit403.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by amit on 10/2/17.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseName {
}
