package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EQReportAdapter extends ArrayAdapter<eqClass> {
    private static final String LOCATION_SEPARATOR = " of ";
    Context context;
    List<eqClass>eq;
    int resource;
    public EQReportAdapter( Context context, int resource,  List<eqClass>Eq) {
        super(context, resource, Eq);
        this.context=context;
        this.resource=resource;
        this.eq=Eq;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {

        // Set the color on the magnitude circle

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_item,null);
        TextView magtext=view.findViewById(R.id.magnitude_view);
        TextView placetext=view.findViewById(R.id.place_view);
        TextView dateView = view.findViewById(R.id.date_view);
        TextView timeView = view.findViewById(R.id.time_view);
        TextView direction=view.findViewById(R.id.direction_view);

        eqClass currentreport=eq.get(position);

        magtext.setText(getformatemag(currentreport.getmagnitude()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magtext.getBackground();
        magnitudeCircle.setColor( getMagnitudeColor(currentreport.getmagnitude()));

        String place=currentreport.getplace();
        if (place.contains(LOCATION_SEPARATOR)) {
            String[] parts =place.split(LOCATION_SEPARATOR);
            direction.setText(parts[0] + LOCATION_SEPARATOR);
            placetext.setText(parts[1]);
        }/* else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            locationOffset = getContext().getString(R.string.near_the);
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = originalLocation;
        }*/

        Date dateObject = new Date(currentreport.getTimeInMilliseconds());
        String formattedDate = getformatDate(dateObject);
        dateView.setText(formattedDate);
        String formattedTime = getformatTime(dateObject);
        timeView.setText(formattedTime);
        return view;
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    private String getformatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String getformatemag(double magnitude)
    {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(magnitude);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String getformatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}
