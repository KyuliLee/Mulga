package com.ilm.mulga.feature.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilm.mulga.R
import com.ilm.mulga.feature.login.component.SocialLoginButton
import com.ilm.mulga.ui.theme.MulGaTheme

@Composable
fun NormalButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = MulGaTheme.colors.primary,
    textColor: Color = MulGaTheme.colors.white1,
    textStyle: TextStyle = MulGaTheme.typography.bodyLarge
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(364.dp)
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(30.dp),
        ) {
        Text(text = text, color = textColor, style = textStyle)
    }
}
