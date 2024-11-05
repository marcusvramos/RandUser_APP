package com.example.randuserface.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserResponse {
    @SerializedName("results")
    public List<User> results;

    public static class User {
        public Name name;
        public Picture picture;
        public Location location;

        public static class Name {
            public String first;
            public String last;
        }

        public static class Picture {
            public String large;
        }

        public static class Location {
            public String city;
            public String country;
            public Coordinates coordinates;

            public static class Coordinates {
                @SerializedName("latitude")
                public String latitude;

                @SerializedName("longitude")
                public String longitude;
            }
        }
    }
}
