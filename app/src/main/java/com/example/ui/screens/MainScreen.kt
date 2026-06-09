package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.database.DownloadedTrack
import com.example.data.services.TrackMeta
import com.example.ui.theme.DarkGreen
import com.example.ui.theme.DeepBlack
import com.example.ui.theme.GlassBg
import com.example.ui.theme.GlassBgListItem
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderAccent
import com.example.ui.theme.GlowGreen
import com.example.ui.theme.LightText
import com.example.ui.theme.MutedText
import com.example.ui.theme.SpotifyGreen
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.SurfaceCard
import com.example.ui.viewmodel.MainViewModel
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import com.example.R

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val userEmail by viewModel.userEmail.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(DeepBlack)
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = DeepBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .drawBehind {
                    // Top-left ambient green glowing mesh bulb (20% opacity)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(DarkGreen.copy(alpha = 0.20f), Color.Transparent),
                            center = Offset(-100f, -100f),
                            radius = size.width * 0.9f
                        ),
                        radius = size.width * 0.9f,
                        center = Offset(-100f, -100f)
                    )
                    // Mid-right/bottom ambient green glowing mesh bulb (10% opacity)
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(SpotifyGreen.copy(alpha = 0.10f), Color.Transparent),
                            center = Offset(size.width * 1.1f, size.height * 0.6f),
                            radius = size.width * 1.1f
                        ),
                        radius = size.width * 1.1f,
                        center = Offset(size.width * 1.1f, size.height * 0.6f)
                    )
                }
        ) {
            if (userEmail == null) {
                LoginView(viewModel = viewModel)
            } else {
                DashboardView(viewModel = viewModel, userEmail = userEmail!!)
            }
        }
    }
}

@Composable
fun LoginView(viewModel: MainViewModel) {
    var emailInput by remember { mutableStateOf("") }
    val loginError by viewModel.loginError.collectAsState()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = GlassBg),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Aesthetic Logo Icon Container
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(SpotifyGreen.copy(alpha = 0.25f), Color.Transparent)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.ic_spotify_logo),
                            contentDescription = "Downspot Logo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "DOWNSPOT",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SpotifyGreen,
                        letterSpacing = 3.sp
                    )
                )

                Text(
                    text = "Music Vault",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        color = LightText
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Seamlessly sync your public Spotify playlists to local storage for offline use.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Email address Input Field
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = {
                        emailInput = it
                        viewModel.onEmailChanged(it)
                    },
                    label = { Text("Email Address", color = MutedText, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email icon",
                            tint = if (loginError != null) MaterialTheme.colorScheme.error else SpotifyGreen
                        )
                    },
                    singleLine = true,
                    isError = loginError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.loginWithEmail(emailInput)
                        }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = LightText,
                        unfocusedTextColor = LightText,
                        focusedBorderColor = SpotifyGreen,
                        unfocusedBorderColor = GlassBorder,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        focusedContainerColor = Color(0x13FFFFFF),
                        unfocusedContainerColor = Color(0x06FFFFFF)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("email_input")
                )

                AnimatedVisibility(visible = loginError != null) {
                    loginError?.let { err ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = err,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.loginWithEmail(emailInput)
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SpotifyGreen,
                        contentColor = DeepBlack
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("login_button")
                ) {
                    Text(
                        text = "SYNC TO DEVICE",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardView(viewModel: MainViewModel, userEmail: String) {
    val playlistUrl by viewModel.playlistUrl.collectAsState()
    val isConverting by viewModel.isConverting.collectAsState()
    val conversionStatus by viewModel.conversionStatus.collectAsState()
    val conversionError by viewModel.conversionError.collectAsState()
    val conversionResult by viewModel.conversionResult.collectAsState()
    val downloadedTracks by viewModel.downloadedTracks.collectAsState()
    val progressState by viewModel.downloadProgressState.collectAsState()
    val currentTrack by viewModel.currentTrack.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Identity Header - styled matching Design HTML
                Column {
                    Text(
                        text = "DOWNSPOT",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = SpotifyGreen,
                            letterSpacing = 2.sp
                        )
                    )
                    Text(
                        text = "Music Vault",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = LightText
                        )
                    )
                }

                // Profile Avatar Row representation as in design template
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val initials = if (userEmail.isNotEmpty()) userEmail.take(2).uppercase() else "JD"

                    // Glass-morphic profile avatar circular widget
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0x1BFFFFFF))
                            .border(1.dp, GlassBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            color = SpotifyGreen,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }

                    IconButton(
                        onClick = { viewModel.logout() },
                        modifier = Modifier
                            .background(Color(0x12FFFFFF), CircleShape)
                            .border(1.dp, GlassBorder, CircleShape)
                            .size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Log Out",
                            tint = LightText,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Link Paste / Prompt Section
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = GlassBg),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "Spotify Playlist Converter",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = LightText
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Paste a Spotify public playlist link or keyword prompt to fetch and compile individual offline songs.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedText
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = playlistUrl,
                                onValueChange = { viewModel.onUrlChanged(it) },
                                label = { Text("Spotify link or keyword...", color = MutedText, fontSize = 13.sp) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Link,
                                        contentDescription = "Link Indicator",
                                        tint = SpotifyGreen
                                    )
                                },
                                trailingIcon = {
                                    if (playlistUrl.isNotEmpty()) {
                                        IconButton(onClick = { viewModel.onUrlChanged("") }) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear",
                                                tint = MutedText
                                            )
                                        }
                                    }
                                },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = LightText,
                                    unfocusedTextColor = LightText,
                                    focusedBorderColor = SpotifyGreen,
                                    unfocusedBorderColor = GlassBorder,
                                    focusedContainerColor = Color(0x13FFFFFF),
                                    unfocusedContainerColor = Color(0x06FFFFFF)
                                ),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("playlist_input")
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    focusManager.clearFocus()
                                    viewModel.convertPlaylist()
                                },
                                enabled = !isConverting && playlistUrl.isNotEmpty(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SpotifyGreen,
                                    contentColor = DeepBlack,
                                    disabledContainerColor = Color(0x11FFFFFF),
                                    disabledContentColor = MutedText
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("convert_button")
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    if (isConverting) {
                                        CircularProgressIndicator(
                                            color = SpotifyGreen,
                                            strokeWidth = 2.dp,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("PARSING PLAYLIST...")
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = "Convert",
                                            tint = DeepBlack,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("SYNC TO DEVICE", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Inline Loading state (frosted glass)
                if (isConverting) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(GlassBg, RoundedCornerShape(20.dp))
                                .border(1.dp, GlassBorder, RoundedCornerShape(20.dp))
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = SpotifyGreen,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = conversionStatus,
                                style = MaterialTheme.typography.bodySmall,
                                color = SpotifyGreen,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // Error State (frosted glass)
                if (conversionError != null) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.12f),
                                    RoundedCornerShape(20.dp)
                                )
                                .border(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Error notification",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = conversionError ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = LightText
                            )
                        }
                    }
                }

                // Conversion Results (Songs ready to download)
                if (conversionResult.isNotEmpty() && !isConverting) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CONVERSION QUEUE",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                ),
                                color = SpotifyGreen
                            )
                            TextButton(
                                onClick = {
                                    conversionResult.forEach { tr ->
                                        // Trigger download if not already saved
                                        val isSaved = downloadedTracks.any { s -> s.title.equals(tr.title, ignoreCase = true) }
                                        if (!isSaved) {
                                            viewModel.downloadTrack(tr)
                                        }
                                    }
                                }
                            ) {
                                Text("DOWNLOAD ALL", color = SpotifyGreen, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }

                    items(conversionResult) { track ->
                        val isSaved = downloadedTracks.any { s -> s.title.equals(track.title, ignoreCase = true) }
                        val progress = progressState[track.title] ?: -1

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = GlassBgListItem),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
                                .testTag("track_item_card")
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Music note thumb - frosted glass styled
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(Color(0x1AFFFFFF), RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MusicNote,
                                        contentDescription = "Song cover",
                                        tint = if (isSaved) SpotifyGreen else MutedText,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                // Song meta
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = track.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = LightText,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${track.artist} • ${formatDuration(track.durationMs)}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MutedText,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                // Interactive action (Download triggers)
                                if (isSaved) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Saved offline",
                                        tint = SpotifyGreen,
                                        modifier = Modifier.size(28.dp).padding(4.dp)
                                    )
                                } else if (progress in 0..99) {
                                    // Progress circle with internal percent indicator
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        CircularProgressIndicator(
                                            progress = { progress / 100f },
                                            color = SpotifyGreen,
                                            strokeWidth = 3.dp,
                                            modifier = Modifier.size(32.dp)
                                        )
                                        Text(
                                            text = "$progress",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = SpotifyGreen
                                        )
                                    }
                                } else {
                                    Button(
                                        onClick = { viewModel.downloadTrack(track) },
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                                        shape = RoundedCornerShape(50),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = SpotifyGreen,
                                            contentColor = DeepBlack
                                        ),
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(92.dp)
                                            .testTag("download_button")
                                    ) {
                                        Text(
                                            text = "DOWNLOAD",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Offline library title
                item {
                    Text(
                        text = "OFFLINE LISTENING LIBRARY",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        ),
                        color = SpotifyGreen,
                        modifier = Modifier.padding(top = 12.dp, start = 4.dp)
                    )
                }

                // Empty state for library
                if (downloadedTracks.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(GlassBg, RoundedCornerShape(24.dp))
                                .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
                                .padding(36.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = "No local songs",
                                tint = MutedText,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Your Local Library is Empty",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = LightText
                            )
                            Text(
                                text = "Pasted links populate files here that you can listen to completely off-grid.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedText,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                // Local track items - glass list layout
                items(downloadedTracks) { localTrack ->
                    val isCurrent = currentTrack?.id == localTrack.id

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isCurrent) Color(0x181DB954) else GlassBgListItem)
                            .border(
                                1.dp, 
                                if (isCurrent) GlassBorderAccent else GlassBorder, 
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { viewModel.playTrack(localTrack) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Speaker status or note
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0x10FFFFFF), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = "Local mp3 file",
                                tint = if (isCurrent) SpotifyGreen else LightText,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = localTrack.title,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) SpotifyGreen else LightText
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${localTrack.artist} • ${formatDuration(localTrack.durationMs)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Playback helper feedback on side
                        if (isCurrent && isPlaying) {
                            // Simple visual green playing sound identifier
                            CircularProgressIndicator(
                                color = SpotifyGreen,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(16.dp).padding(2.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }

                        IconButton(
                            onClick = { viewModel.deleteTrack(localTrack) },
                            modifier = Modifier.testTag("delete_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete offline local track copy",
                                tint = MutedText.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Spacer at bottom to guarantee visual clearance above fixed playing bar
                item {
                    Spacer(modifier = Modifier.height(130.dp))
                }
            }
        }

        // Global playback bottom bar (floating above screen base)
        AnimatedVisibility(
            visible = currentTrack != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            currentTrack?.let { track ->
                val progress by viewModel.playbackProgress.collectAsState()
                val positionMs by viewModel.playbackPositionMs.collectAsState()
                val durationMs by viewModel.playbackDurationMs.collectAsState()

                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x22FFFFFF)), // High clarity glassmorphism background
                    elevation = CardDefaults.cardElevation(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SpotifyGreen.copy(alpha = 0.35f), RoundedCornerShape(24.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(Color(0x16FFFFFF), RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MusicNote,
                                        contentDescription = "Playing cover arts",
                                        tint = SpotifyGreen,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = track.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = LightText,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = track.artist,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = SpotifyGreen,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(SpotifyGreen)
                                        .size(40.dp)
                                        .clickable { viewModel.togglePlayPause() }
                                        .testTag("play_pause_button"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isPlaying) {
                                            // Dedicated standard Pause icon
                                            Icons.Default.Pause
                                        } else {
                                            Icons.Default.PlayArrow
                                        },
                                        contentDescription = "Play/Pause toggling",
                                        tint = DeepBlack,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Audio Seek bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatDuration(positionMs.toLong()),
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = MutedText,
                                modifier = Modifier.width(36.dp)
                            )

                            // Clean slider seeker (frosted accent colors)
                            Slider(
                                value = progress,
                                onValueChange = { viewModel.seekTo(it) },
                                colors = SliderDefaults.colors(
                                    thumbColor = SpotifyGreen,
                                    activeTrackColor = SpotifyGreen,
                                    inactiveTrackColor = Color(0x1CFFFFFF)
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = formatDuration(durationMs.toLong()),
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                color = MutedText,
                                modifier = Modifier.width(36.dp),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatDuration(ms: Long): String {
    val totalSecs = ms / 1000
    val mins = totalSecs / 60
    val secs = totalSecs % 60
    return String.format("%02d:%02d", mins, secs)
}
