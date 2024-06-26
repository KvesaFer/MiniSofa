package com.sofascore.minisofa.utils

class CountryCodes {
    private val map: Map<String, String> = mapOf(
        "Andorra, Principality Of" to "AD",
        "United Arab Emirates" to "AE",
        "Afghanistan, Islamic State Of" to "AF",
        "Antigua And Barbuda" to "AG",
        "Anguilla" to "AI",
        "Albania" to "AL",
        "Armenia" to "AM",
        "Netherlands Antilles" to "AN",
        "Angola" to "AO",
        "Antarctica" to "AQ",
        "Argentina" to "AR",
        "American Samoa" to "AS",
        "Austria" to "AT",
        "Australia" to "AU",
        "Aruba" to "AW",
        "Azerbaidjan" to "AZ",
        "Bosnia-Herzegovina" to "BA",
        "Barbados" to "BB",
        "Bangladesh" to "BD",
        "Belgium" to "BE",
        "Burkina Faso" to "BF",
        "Bulgaria" to "BG",
        "Bahrain" to "BH",
        "Burundi" to "BI",
        "Benin" to "BJ",
        "Bermuda" to "BM",
        "Brunei Darussalam" to "BN",
        "Bolivia" to "BO",
        "Brazil" to "BR",
        "Bahamas" to "BS",
        "Bhutan" to "BT",
        "Bouvet Island" to "BV",
        "Botswana" to "BW",
        "Belarus" to "BY",
        "Belize" to "BZ",
        "Canada" to "CA",
        "Cocos (Keeling) Islands" to "CC",
        "Central African Republic" to "CF",
        "Congo, The Democratic Republic Of The" to "CD",
        "Congo" to "CG",
        "Switzerland" to "CH",
        "Ivory Coast (Cote D'Ivoire)" to "CI",
        "Cook Islands" to "CK",
        "Chile" to "CL",
        "Cameroon" to "CM",
        "China" to "CN",
        "Colombia" to "CO",
        "Costa Rica" to "CR",
        "Former Czechoslovakia" to "CS",
        "Cuba" to "CU",
        "Cape Verde" to "CV",
        "Christmas Island" to "CX",
        "Cyprus" to "CY",
        "Czech Republic" to "CZ",
        "Germany" to "DE",
        "Djibouti" to "DJ",
        "Denmark" to "DK",
        "Dominica" to "DM",
        "Dominican Republic" to "DO",
        "Algeria" to "DZ",
        "Ecuador" to "EC",
        "Estonia" to "EE",
        "Egypt" to "EG",
        "Western Sahara" to "EH",
        "Eritrea" to "ER",
        "Spain" to "ES",
        "Ethiopia" to "ET",
        "Finland" to "FI",
        "Fiji" to "FJ",
        "Falkland Islands" to "FK",
        "Micronesia" to "FM",
        "Faroe Islands" to "FO",
        "France" to "FR",
        "France (European Territory)" to "FX",
        "Gabon" to "GA",
        "Great Britain" to "UK",
        "Grenada" to "GD",
        "Georgia" to "GE",
        "French Guyana" to "GF",
        "Ghana" to "GH",
        "Gibraltar" to "GI",
        "Greenland" to "GL",
        "Gambia" to "GM",
        "Guinea" to "GN",
        "Guadeloupe (French)" to "GP",
        "Equatorial Guinea" to "GQ",
        "Greece" to "GR",
        "S. Georgia & S. Sandwich Isls." to "GS",
        "Guatemala" to "GT",
        "Guam (USA)" to "GU",
        "Guinea Bissau" to "GW",
        "Guyana" to "GY",
        "Hong Kong" to "HK",
        "Heard And McDonald Islands" to "HM",
        "Honduras" to "HN",
        "Croatia" to "HR",
        "Haiti" to "HT",
        "Hungary" to "HU",
        "Indonesia" to "ID",
        "Ireland" to "IE",
        "Israel" to "IL",
        "India" to "IN",
        "British Indian Ocean Territory" to "IO",
        "Iraq" to "IQ",
        "Iran" to "IR",
        "Iceland" to "IS",
        "Italy" to "IT",
        "Jamaica" to "JM",
        "Jordan" to "JO",
        "Japan" to "JP",
        "Kenya" to "KE",
        "Kyrgyz Republic (Kyrgyzstan)" to "KG",
        "Cambodia, Kingdom Of" to "KH",
        "Kiribati" to "KI",
        "Comoros" to "KM",
        "Saint Kitts & Nevis Anguilla" to "KN",
        "North Korea" to "KP",
        "South Korea" to "KR",
        "Kuwait" to "KW",
        "Cayman Islands" to "KY",
        "Kazakhstan" to "KZ",
        "Laos" to "LA",
        "Lebanon" to "LB",
        "Saint Lucia" to "LC",
        "Liechtenstein" to "LI",
        "Sri Lanka" to "LK",
        "Liberia" to "LR",
        "Lesotho" to "LS",
        "Lithuania" to "LT",
        "Luxembourg" to "LU",
        "Latvia" to "LV",
        "Libya" to "LY",
        "Morocco" to "MA",
        "Monaco" to "MC",
        "Moldavia" to "MD",
        "Madagascar" to "MG",
        "Marshall Islands" to "MH",
        "Macedonia" to "MK",
        "Mali" to "ML",
        "Myanmar" to "MM",
        "Mongolia" to "MN",
        "Macau" to "MO",
        "Northern Mariana Islands" to "MP",
        "Martinique (French)" to "MQ",
        "Mauritania" to "MR",
        "Montserrat" to "MS",
        "Malta" to "MT",
        "Mauritius" to "MU",
        "Maldives" to "MV",
        "Malawi" to "MW",
        "Mexico" to "MX",
        "Malaysia" to "MY",
        "Mozambique" to "MZ",
        "Namibia" to "NA",
        "New Caledonia (French)" to "NC",
        "Niger" to "NE",
        "Norfolk Island" to "NF",
        "Nigeria" to "NG",
        "Nicaragua" to "NI",
        "Netherlands" to "NL",
        "Norway" to "NO",
        "Nepal" to "NP",
        "Nauru" to "NR",
        "Neutral Zone" to "NT",
        "Niue" to "NU",
        "New Zealand" to "NZ",
        "Oman" to "OM",
        "Panama" to "PA",
        "Peru" to "PE",
        "Polynesia (French)" to "PF",
        "Papua New Guinea" to "PG",
        "Philippines" to "PH",
        "Pakistan" to "PK",
        "Poland" to "PL",
        "Saint Pierre And Miquelon" to "PM",
        "South Africa" to "ZA",
        "Pitcairn Island" to "PN",
        "Puerto Rico" to "PR",
        "Portugal" to "PT",
        "Palau" to "PW",
        "Paraguay" to "PY",
        "Qatar" to "QA",
        "Reunion (French)" to "RE",
        "Romania" to "RO",
        "Russian Federation" to "RU",
        "Rwanda" to "RW",
        "Saudi Arabia" to "SA",
        "Solomon Islands" to "SB",
        "Seychelles" to "SC",
        "Sudan" to "SD",
        "Sweden" to "SE",
        "Singapore" to "SG",
        "Saint Helena" to "SH",
        "Slovenia" to "SI",
        "Svalbard And Jan Mayen Islands" to "SJ",
        "Slovak Republic" to "SK",
        "Sierra Leone" to "SL",
        "San Marino" to "SM",
        "Senegal" to "SN",
        "Somalia" to "SO",
        "Suriname" to "SR",
        "Saint Tome (Sao Tome) And Principe" to "ST",
        "Former USSR" to "SU",
        "El Salvador" to "SV",
        "Syria" to "SY",
        "Swaziland" to "SZ",
        "Turks And Caicos Islands" to "TC",
        "Chad" to "TD",
        "French Southern Territories" to "TF",
        "Togo" to "TG",
        "Thailand" to "TH",
        "Tadjikistan" to "TJ",
        "Tokelau" to "TK",
        "Turkmenistan" to "TM",
        "Tunisia" to "TN",
        "Tonga" to "TO",
        "East Timor" to "TP",
        "Turkey" to "TR",
        "Trinidad And Tobago" to "TT",
        "Tuvalu" to "TV",
        "Taiwan" to "TW",
        "Tanzania" to "TZ",
        "Ukraine" to "UA",
        "Uganda" to "UG",
        "United Kingdom" to "GB",
        "England" to "GB",
        "USA Minor Outlying Islands" to "UM",
        "United States" to "US",
        "USA" to "US",
        "Uruguay" to "UY",
        "Uzbekistan" to "UZ",
        "Holy See (Vatican City State)" to "VA",
        "Saint Vincent & Grenadines" to "VC",
        "Venezuela" to "VE",
        "Virgin Islands (British)" to "VG",
        "Virgin Islands (USA)" to "VI",
        "Vietnam" to "VN",
        "Vanuatu" to "VU",
        "Wallis And Futuna Islands" to "WF",
        "Samoa" to "WS",
        "Yemen" to "YE",
        "Mayotte" to "YT"
    )

    fun getCountryCode(countryName: String): String? {
        return map[countryName]
    }
}
