package com.mikhail.weatherclient;

import android.os.Handler;

import com.mikhail.weatherclient.model.WeatherModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherProvider {
    private Set<WeatherProviderListener> listeners;
    private Timer timer;
    private static WeatherProvider instance = null;
    private Handler handler = new Handler();
    private SimpleDateFormat hours = new SimpleDateFormat("HH", Locale.ENGLISH);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private WeatherModel weatherModel;
    private ArrayList<String> nexttime;
    private Retrofit retrofit;
    private OpenWeather weatherByCityName;
    private OpenWeatherByCoords weatherByCoords;
    private boolean isWeatherByCoords;
    private boolean isFirstDownload = true;

    private WeatherProvider() {
        listeners = new HashSet<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        startData();
    }

    public void setWeatherByCoords(boolean weatherByCoords) {
        isWeatherByCoords = weatherByCoords;
    }

    public boolean isWeatherByCoords() {
        return isWeatherByCoords;
    }

    interface OpenWeather {
        @GET("data/2.5/forecast")
        Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String key);
    }

    interface OpenWeatherByCoords {
        @GET("data/2.5/forecast")
        Call<WeatherModel> getWeatherByCoords(@Query("lat") Float lat, @Query("lon") Float lon, @Query("appid") String key);
        // http://api.openweathermap.org/data/2.5/forecast?lat=12&lon=13.0&appid=bf4ee7b1e28b2bbea359e78e1ebbbd78
        //lat={lat}&lon={lon}\n")
    }

    public WeatherModel getWeatherModel() {
        return weatherModel;
    }

    public static WeatherProvider getInstance() {
        instance = instance == null ? new WeatherProvider() : instance;
        return instance;
    }

    public void addListener(WeatherProviderListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WeatherProviderListener listener) {
        listeners.remove(listener);
    }

    /*private WeatherModel getWeather(String cityname) {
        String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityname + ",RU&appid=0507febdbdf6a636ec6bdcdfe0b909fc";
        WeatherModel model = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                final String result = in.lines().collect(Collectors.joining("\n"));
                Gson gson = new Gson();
                model = gson.fromJson(result, WeatherModel.class);
                City_changerPresenter.getInstance().setMistake(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return model;
    }*/

    public void setFirstDownload(boolean firstDownload) {
        isFirstDownload = firstDownload;
    }

    public boolean isFirstDownload() {
        return isFirstDownload;
    }

    private WeatherModel getWeather(String cityname) throws Exception {
        Call<WeatherModel> call = weatherByCityName.getWeather(cityname, "0507febdbdf6a636ec6bdcdfe0b909fc");
        Response<WeatherModel> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            setFirstDownload(true);
            throw new Exception(response.errorBody() != null ? response.errorBody().string() : null, null);
        }
    }

    private WeatherModel getWeatherByCoords(Float lat, Float lon) throws Exception {
        Call<WeatherModel> call = weatherByCoords.getWeatherByCoords(lat, lon, "0507febdbdf6a636ec6bdcdfe0b909fc");
        Response<WeatherModel> response = call.execute();
        if (response.isSuccessful()) return response.body();
        else
            throw new Exception(response.errorBody() != null ? response.errorBody().string() : null, null);
    }

    private ArrayList<String> getNextTime(WeatherModel model) {
        ArrayList<String> result = new ArrayList<>();
        try {
            for (int i = 0; i < model.getList().size(); i++) {
                Date d = simpleDateFormat.parse(model.getList().get(i).getDtTxt());
                int a = Integer.valueOf(hours.format(Objects.requireNonNull(d)));
                result.add(String.valueOf(a));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getDays() {
        String[] result = new String[5];
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            result[i] = (c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
            c.add(Calendar.DAY_OF_WEEK, +1);
        }
        return result;
    }

    private void startData() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (isFirstDownload) {
                        //if (!isWeatherByCoords) {
                        weatherByCityName = retrofit.create(OpenWeather.class);
                        weatherModel = getWeather(City_changerPresenter.getInstance().getCityName());
                        // } else{
                        // weatherByCoords = retrofit.create(OpenWeatherByCoords.class);
                        // weatherModel = getWeatherByCoords(City_changerPresenter.getInstance().getLat(), City_changerPresenter.getInstance().getLon());
                        //  }
                        isFirstDownload = false;
                    }
                    nexttime = getNextTime(weatherModel);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (WeatherProviderListener listener : listeners) {
                                listener.updateWeather(weatherModel, nexttime);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    void stop() {
        if (timer != null) {
            timer.cancel();
        }
        listeners.clear();
    }

    public String tempInGradus(int numberofmodel) {
        if (weatherModel == null) return null;
        double temp = (weatherModel.getList().get(numberofmodel).getMain().getTemp() - 273.15);
        double tempInCelvin = new BigDecimal(Double.toString(temp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf((int) tempInCelvin);
    }

    public String tempInFahrenheit(int numberofmodel) {
        if (weatherModel == null) return null;
        double temp = (weatherModel.getList().get(numberofmodel).getMain().getTemp() - 273.15) * 1.8000 + 32.00;
        return String.valueOf((int) temp);
    }

    public String getWindSpeed() {
        double windSpeed = new BigDecimal(Double.toString(weatherModel.getList().get(0).getWind().getSpeed())).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf((int) windSpeed);
    }


    public String[] tempMinToWeekInFahrenheit() {
        String[] result = new String[weatherModel.getList().size()];
        int i = 0;
        for (int j = 0; j < result.length; j += 8) {
            result[i] = getMinTempTodayInFahrenheit(j);
            i++;
        }
        return result;
    }

    private String getMinTempTodayInFahrenheit(int counter) {
        double minTemp = 273.15;
        double tempnow;
        for (int i = counter; i < counter + 8; i++) {
            tempnow = weatherModel.getList().get(i).getMain().getTempMin();
            if (tempnow < minTemp)
                minTemp = tempnow;
        }
        minTemp = (minTemp - 273.15) * 1.8000 + 32.00;
        double tempInCelvin = new BigDecimal(Double.toString(minTemp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf((int) tempInCelvin);
    }

    public String[] tempMaxToWeekInFahrenheit() {
        String[] result = new String[weatherModel.getList().size()];
        int i = 0;
        for (int j = 0; j < result.length; j += 8) {
            result[i] = getMaxTempTodayInFahrenheit(j);
            i++;
        }
        return result;
    }

    private String getMaxTempTodayInFahrenheit(int counter) {
        double maxTemp = 273.15;
        double tempnow;
        for (int i = counter; i < counter + 8; i++) {
            tempnow = weatherModel.getList().get(i).getMain().getTempMax();
            if (tempnow < maxTemp)
                maxTemp = tempnow;
        }
        maxTemp = (maxTemp - 273.15) * 1.8000 + 32.00;
        double tempInCelvin = new BigDecimal(Double.toString(maxTemp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf((int) tempInCelvin);
    }

    public String[] tempMinToWeekInCelsius() {
        String[] result = new String[weatherModel.getList().size()];
        int i = 0;
        for (int j = 0; j < result.length; j += 8) {
            result[i] = getMinTempTodayInCelsius(j);
            i++;
        }
        return result;
    }

    private String getMinTempTodayInCelsius(int counter) {
        double minTemp = 273.15;
        double tempnow;
        for (int i = counter; i < counter + 8; i++) {
            tempnow = weatherModel.getList().get(i).getMain().getTempMin();
            if (tempnow < minTemp)
                minTemp = tempnow;
        }
        minTemp = minTemp - 273.15;
        double tempInCelvin = new BigDecimal(Double.toString(minTemp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf((int) tempInCelvin);
    }

    public String[] tempMaxToWeekInCelsius() {
        String[] result = new String[weatherModel.getList().size()];
        int i = 0;
        for (int j = 0; j < result.length; j += 8) {
            result[i] = getMaxTempTodayInCelsius(j);
            i++;
        }
        return result;
    }

    private String getMaxTempTodayInCelsius(int counter) {
        double maxtemp = 273.15;
        double tempnow;
        for (int i = counter; i < counter + 8; i++) {
            tempnow = weatherModel.getList().get(i).getMain().getTempMax();
            if (tempnow > maxtemp)
                maxtemp = tempnow;
        }
        maxtemp = maxtemp - 273.15;
        double tempInCelvin = new BigDecimal(Double.toString(maxtemp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf((int) tempInCelvin);
    }
}
