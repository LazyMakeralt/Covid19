package com.example.covid19.ui.screens.country


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid19.R
import com.example.covid19.model.CountryStat
import com.example.covid19.model.MainViewModel






@Composable
fun CountryScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController?,
    mainViewModel: MainViewModel = viewModel()
) {
    val countryStats = mainViewModel.countryStats.collectAsState().value
    val errorMessage by mainViewModel.errorMessage.collectAsState()
    val isLoading by mainViewModel._runInProgress.collectAsState()

    var selectedCountry by remember { mutableStateOf<CountryStat?>(null) }
    var searchText by remember { mutableStateOf("") } // searching text

    // Filter with text
    val filteredCountries = countryStats.filter { countryStat ->
        countryStat.countryName.contains(searchText, ignoreCase = true)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(16.dp)
    ) {
        Text(
            text = "Country Statistics",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(bottom = 16.dp, top = 50.dp)
        )

        // Searchbar
        SearchBar(searchText = searchText) { newText ->
            searchText = newText
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navHostController?.navigate("homeScreen") },
                modifier = Modifier.weight(1f).padding(end = 8.dp, top = 10.dp)
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Back")
            }
            Button(
                onClick = { mainViewModel.loadCovidCountry() },
                modifier = Modifier.weight(1f).padding(start = 8.dp, top = 10.dp)
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Load Data",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load Data ")
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(filteredCountries) { stat ->
                    CountryStatCard(
                        stat = stat,
                        onClick = { selectedCountry = stat }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    // Popup
    selectedCountry?.let { country ->
        CountryPopup(
            country = country,
            onDismiss = { selectedCountry = null }
        )
    }
}

@Composable
fun CountryStatCard(stat: CountryStat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
        ) {
            stat.image?.let { imageResId ->
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "${stat.countryName} Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Text(
                text = stat.continentName.take(1),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stat.countryName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.scrim
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total Cases: ${stat.totalCases}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.scrim
            )
            Text(
                text = "Total Deaths: ${stat.totalDeaths}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.scrim
            )
            Text(
                text = "......",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.scrim
            )
        }
    }
}

@Composable
fun CountryPopup(country: CountryStat, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = country.countryName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Continent: ${country.continentName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Cases: ${country.totalCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Deaths: ${country.totalDeaths}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Tests: ${country.totalTests}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Critical Cases: ${country.totalCriticalCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Recovered Cases: ${country.totalRecoveredCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Active Cases: ${country.totalActiveCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    TextField(
        value = searchText, // default value of the searchbar
        onValueChange = onSearchTextChange, // value of the searchbar
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { Text("Enter country name") },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(top = 16.dp)
    )
}
fun getCountryFlagResource(countryName: String): Int {
    return when (countryName.lowercase()) {
        "afghanistan" -> R.raw.af
        "albania" -> R.raw.al
        "algeria" -> R.raw.dz
        "andorra" -> R.raw.ad
        "angola" -> R.raw.ao
        "antigua and barbuda" -> R.raw.ag
        "argentina" -> R.raw.ar
        "armenia" -> R.raw.am
        "australia" -> R.raw.au
        "austria" -> R.raw.at
        "azerbaijan" -> R.raw.az
        "bahamas" -> R.raw.bs
        "bahrain" -> R.raw.bh
        "bangladesh" -> R.raw.bd
        "barbados" -> R.raw.bb
        "belarus" -> R.raw.by
        "belgium" -> R.raw.be
        "belize" -> R.raw.bz
        "benin" -> R.raw.bj
        "bhutan" -> R.raw.bt
        "bolivia" -> R.raw.bo
        "bosnia and herzegovina" -> R.raw.ba
        "botswana" -> R.raw.bw
        "brazil" -> R.raw.br
        "brunei" -> R.raw.bn
        "bulgaria" -> R.raw.bg
        "burkina faso" -> R.raw.bf
        "burundi" -> R.raw.bi
        "cabo verde" -> R.raw.cv
        "cambodia" -> R.raw.kh
        "cameroon" -> R.raw.cm
        "canada" -> R.raw.ca
        "chile" -> R.raw.cl
        "china" -> R.raw.cn
        "colombia" -> R.raw.co
        "comoros" -> R.raw.km
        "congo" -> R.raw.cg
        "congo (democratic republic)" -> R.raw.cd
        "costa rica" -> R.raw.cr
        "croatia" -> R.raw.hr
        "cuba" -> R.raw.cu
        "cyprus" -> R.raw.cy
        "czech republic" -> R.raw.cz
        "denmark" -> R.raw.dk
        "djibouti" -> R.raw.dj
        "dominica" -> R.raw.dm
        "dominican republic" -> R.raw.placeholder
            "ecuador" -> R.raw.ec
        "egypt" -> R.raw.eg
        "el salvador" -> R.raw.sv
        "equatorial guinea" -> R.raw.gq
        "eritrea" -> R.raw.er
        "estonia" -> R.raw.ee
        "eswatini" -> R.raw.sz
        "ethiopia" -> R.raw.et
        "fiji" -> R.raw.fj
        "finland" -> R.raw.fi
        "france" -> R.raw.fr
        "gabon" -> R.raw.ga
        "gambia" -> R.raw.gm
        "georgia" -> R.raw.ge
        "germany" -> R.raw.de
        "ghana" -> R.raw.gh
        "greece" -> R.raw.gr
        "grenada" -> R.raw.gd
        "guatemala" -> R.raw.gt
        "guinea" -> R.raw.gn
        "guinea-bissau" -> R.raw.gw
        "guyana" -> R.raw.gy
        "haiti" -> R.raw.ht
        "honduras" -> R.raw.hn
        "hungary" -> R.raw.hu
        "iceland" -> R.raw.placeholder
        "india" -> R.raw.placeholder
        "indonesia" -> R.raw.id
        "iran" -> R.raw.ir
        "iraq" -> R.raw.iq
        "ireland" -> R.raw.ie
        "israel" -> R.raw.il
        "italy" -> R.raw.it
        "jamaica" -> R.raw.jm
        "japan" -> R.raw.jp
        "jordan" -> R.raw.jo
        "kazakhstan" -> R.raw.kz
        "kenya" -> R.raw.ke
        "kiribati" -> R.raw.ki
        "kosovo" -> R.raw.placeholder
        "kuwait" -> R.raw.kw
        "kyrgyzstan" -> R.raw.kg
        "laos" -> R.raw.la
        "latvia" -> R.raw.lv
        "lebanon" -> R.raw.lb
        "lesotho" -> R.raw.ls
        "liberia" -> R.raw.lr
        "libya" -> R.raw.ly
        "liechtenstein" -> R.raw.li
        "lithuania" -> R.raw.lt
        "luxembourg" -> R.raw.lu
        "madagascar" -> R.raw.mg
        "malawi" -> R.raw.mw
        "malaysia" -> R.raw.my
        "maldives" -> R.raw.mv
        "mali" -> R.raw.ml
        "malta" -> R.raw.mt
        "marshall islands" -> R.raw.mh
        "mauritania" -> R.raw.mr
        "mauritius" -> R.raw.mu
        "mexico" -> R.raw.mx
        "micronesia" -> R.raw.fm
        "moldova" -> R.raw.md
        "monaco" -> R.raw.mc
        "mongolia" -> R.raw.mn
        "montenegro" -> R.raw.me
        "morocco" -> R.raw.ma
        "mozambique" -> R.raw.mz
        "myanmar" -> R.raw.mm
        "namibia" -> R.raw.na
        "nauru" -> R.raw.nr
        "nepal" -> R.raw.np
        "netherlands" -> R.raw.nl
        "new zealand" -> R.raw.nz
        "nicaragua" -> R.raw.ni
        "niger" -> R.raw.ne
        "nigeria" -> R.raw.ng
        "north korea" -> R.raw.kp
        "north macedonia" -> R.raw.mk
        "norway" -> R.raw.no
        "oman" -> R.raw.om
        "pakistan" -> R.raw.pk
        "palau" -> R.raw.pw
        "panama" -> R.raw.pa
        "papua new guinea" -> R.raw.pg
        "paraguay" -> R.raw.py
        "peru" -> R.raw.pe
        "philippines" -> R.raw.ph
        "poland" -> R.raw.pl
        "portugal" -> R.raw.pt
        "qatar" -> R.raw.qa
        "romania" -> R.raw.ro
        "russia" -> R.raw.ru
        "rwanda" -> R.raw.rw
        "saint kitts and nevis" -> R.raw.kn
        "saint lucia" -> R.raw.lc
        "saint vincent and the grenadines" -> R.raw.vc
        "samoa" -> R.raw.ws
        "san marino" -> R.raw.sm
        "sao tome and principe" -> R.raw.st
        "saudi arabia" -> R.raw.sa
        "senegal" -> R.raw.sn
        "serbia" -> R.raw.rs
        "seychelles" -> R.raw.sc
        "sierra leone" -> R.raw.sl
        "singapore" -> R.raw.sg
        "slovakia" -> R.raw.sk
        "slovenia" -> R.raw.si
        "solomon islands" -> R.raw.sb
        "somalia" -> R.raw.so
        "south africa" -> R.raw.za
        "south korea" -> R.raw.kr
        "south sudan" -> R.raw.ss
        "spain" -> R.raw.es
        "sri lanka" -> R.raw.lk
        "sudan" -> R.raw.sd
        "suriname" -> R.raw.sr
        "sweden" -> R.raw.se
        "switzerland" -> R.raw.ch
        "syria" -> R.raw.sy
        "taiwan" -> R.raw.tw
        "tajikistan" -> R.raw.tj
        "tanzania" -> R.raw.tz
        "thailand" -> R.raw.th
        "togo" -> R.raw.tg
        "tonga" -> R.raw.to
        "trinidad and tobago" -> R.raw.tt
        "tunisia" -> R.raw.tn
        "turkey" -> R.raw.tr
        "turkmenistan" -> R.raw.tm
        "tuvalu" -> R.raw.tv
        "uganda" -> R.raw.ug
        "ukraine" -> R.raw.ua
        "united arab emirates" -> R.raw.ae
        "united kingdom" -> R.raw.gb
        "united states" -> R.raw.us
        "uruguay" -> R.raw.uy
        "uzbekistan" -> R.raw.uz
        "vanuatu" -> R.raw.vu
        "vatican city" -> R.raw.va
        "venezuela" -> R.raw.ve
        "vietnam" -> R.raw.vn
        "yemen" -> R.raw.ye
        "zambia" -> R.raw.zm
        "zimbabwe" -> R.raw.zw
        else -> R.raw.placeholder // Default flag is not exist
    }
}




