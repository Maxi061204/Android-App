package app.api.quiz;

public class SubSpecificQuiz {

    private String description, name, coordinates;

    private double longitude, latitude;

    public SubSpecificQuiz(String description, String name, String coordinates){
        this.description = description;
        this.name = name;
        this.coordinates = coordinates;

        String[] split = this.coordinates.split(" ");
        latitude = Double.parseDouble(split[0].replace("N", ""));
        longitude = Double.parseDouble(split[1].replace("E", ""));
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
