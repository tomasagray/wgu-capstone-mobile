/*
 * Copyright (c) 2020 Tomás Gray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.wgu.student.tomasgray.capstone.data.model;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Converters
{
    private static final String LOG_TAG = "Converters";

    private static final Gson gson = new Gson();


    // LocalDate
    // ---------------------------------------------------------------------------------
    @TypeConverter
    public static LocalDate localDateFromString(String date) {
//        Log.i(LOG_TAG, "Converting String to LocalDate: " + date);
        return (date == null) ? null : LocalDate.parse(date);
    }
    @TypeConverter
    public static String localDateToString(LocalDate date) {
//        Log.i(LOG_TAG, "Converting LocalDate to String: " + date.toString());
        return (date == null) ? null : date.toString();
    }


    // UUID
    // ---------------------------------------------------------------------------------
    @TypeConverter
    public static UUID uuidFromString(String uuid) {
        return (uuid == null) ? null : UUID.fromString(uuid);
    }
    @TypeConverter
    public static String uuidToString(UUID uuid) {
        return (uuid == null) ? null : uuid.toString();
    }

    // Course.Status
    // --------------------------------------------------------------------------------
    @TypeConverter
    public static Course.Status statusFromString(String status) {
//        Log.i(LOG_TAG, "Converting to Course.Status from String: " + status);
        return (status == null) ? null : Course.Status.valueOf(status);
    }
    @TypeConverter
    public static String statusToString(Course.Status status) {
        return (status == null) ? null : status.name();
    }

    // Assessment.AssessmentType
    // -------------------------------------------------------------------------------
    @TypeConverter
    public static Assessment.AssessmentType typeFromString(String type) {
        return (type == null) ? null : Assessment.AssessmentType.valueOf(type);
    }
    @TypeConverter
    public static String typeToString(Assessment.AssessmentType type) {
        return (type == null) ? null : type.name();
    }

    // Assessment.AssessmentItem
    // -------------------------------------------------------------------------------
    @TypeConverter
    public static List<Assessment.AssessmentItem> itemListFromString(String itemList) {
//        Log.i(LOG_TAG, "Converting String to List of AssessmentItems: " + itemList);
        Type listType = new TypeToken<ArrayList<Assessment.AssessmentItem>>(){}.getType();
        return gson.fromJson(itemList, listType);
    }
    @TypeConverter
    public static String itemListToString(List<Assessment.AssessmentItem> items) {
//        Log.i(LOG_TAG, "Converting List of AssessmentItems to JSON string: " + items);
        return gson.toJson(items);
    }

    // Address
    // ------------------------------------------------------------------------------
    @TypeConverter
    public static Address addressFromString(String address) {
        Log.i(LOG_TAG, "Converting address... " + address);
        return (address == null) ? null : gson.fromJson(address, Address.class);
    }
    @TypeConverter
    public static String addressToString(Address address) {
        return (address == null) ? null : gson.toJson(address);
    }
}
