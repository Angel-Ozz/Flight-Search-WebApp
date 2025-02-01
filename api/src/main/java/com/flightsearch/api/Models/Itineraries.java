package com.flightsearch.api.Models;

import java.time.Duration;
import java.util.List;

public class Itineraries {
    private Duration duration;
    private List <Segments> segments;

    public Itineraries() {
    }

    public Itineraries(Duration duration, List<Segments> segments) {
        this.duration = duration;
        this.segments = segments;
    }

    public Duration getDuration() {
        return duration;
    }
    
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<Segments> getSegments() {
        return segments;
    }
    
    public void setSegments(List<Segments> segments) {
        this.segments = segments;
    }
    


}
