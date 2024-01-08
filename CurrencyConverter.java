package Test;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {
    public static final String ACCESS_KEY = "d937e56102d0fbe603a0173cf164b224";
    private static final String API_URL= "https://api.exchangerate.host/latest";
    public static final String ENDPOINT = "live";

    public static void main(String[] args)
    {
        try {
            CurrencyConverter con= new CurrencyConverter();

            while (true)
            {
                System.out.println("Currency Converter Requirements:-");
                System.out.println("   1. Currency Converter");
                System.out.println("   2. View favourite Currencies");
                System.out.println("   3. Add favourite Currency");
                System.out.println("   4. Update favourite Currency");
                System.out.println("   5. Exit");
                System.out.println("Select an Option:-");

                try {
                    BufferedReader BF =new BufferedReader(new InputStreamReader(System.in));
                    int key=Integer.parseInt(BF.readLine());

                    switch (key) {
                        case 1:convertCurrency();
                            break;
                        case 2:DisplayFavouriteCurrencies();
                            break;
                        case 3:addFavouriteCurrency();
                            break;
                        case 4:updateFavouriteCurrency();
                            break;
                        case 5:System.exit(0);

                        default:System.out.println("Inavlid option,Please make sure to enter a Valid Option.");
                            break;
                    }
                }catch (IOException | NumberFormatException e)
                {
                    System.out.println("InValid input,Please try again..");
                }
                double amount = getUserInput();
                String sourceCurrency = getUserCurrency("Enter a currency ConvertFrom (e.g., USD):  ");
                String targetCurrency = getUserCurrency("Enter the currency ConvertTo(e.g., EUR):  ");
                String favoriteCurrency = getUserCurrency("Enter your favorite currency (e.g.,LYD): ");

                double convertingAmount = con.convertCurrency(amount, sourceCurrency, targetCurrency);
                double favoriteAmount = con.convertCurrency(amount, sourceCurrency, favoriteCurrency);

                System.out.println(String.format("%.2f %s = %.2f %s", amount, sourceCurrency, convertingAmount, targetCurrency));
                System.out.println(String.format("%.2f %s = %.2f %s (your favorite)", amount, sourceCurrency, favoriteAmount, favoriteCurrency));
                System.out.print("Do you want to perform another conversion? (yes/no): ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String choice = reader.readLine().trim().toLowerCase();
                if (!choice.equals("yes")) {
                    System.out.println("Thank you!!!!!!!!!!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid .please try again");
        }
    }
    private static void exchangeRates() {
        // TODO Auto-generated method stub

    }
    private static void updateFavouriteCurrency() {
        // TODO Auto-generated method stub
    }
    private static void addFavouriteCurrency() {
        // TODO Auto-generated method stub

    }
    private static void DisplayFavouriteCurrencies() {
    }
    private static void convertCurrency() {
        // TODO Auto-generated method stud
    }

    private static double getUserInput() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter Amt: ");
        String input = reader.readLine();
        return Double.parseDouble(input);
    }
    private static String getUserCurrency(String message) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(message);
        return reader.readLine().toUpperCase();
    }
    private double convertCurrency(double amount, String sourceCurrency, String targetCurrency) throws IOException, JSONException
    {

        String requestUrl = API_URL + "?base=" +sourceCurrency + "&symbols=" + targetCurrency;
        URL url = new URL(requestUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Access_Key", "d937e56102d0fbe603a0173cf164b224");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null)
        {
            response.append(line);
        }
        reader.close();
        connection.disconnect();
        System.out.println("Response from API: " + response.toString());

        JSONObject jsonResponse = new JSONObject(response.toString());
        double exchangeRate = jsonResponse.getJSONObject("rates").getDouble(targetCurrency);

        return amount * exchangeRate;
    }
}
