package com.kelompok.biodata.model;

import jakarta.persistence.*;

@Entity
@Table(name = "theme_settings")
public class ThemeSetting {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private String primaryColor = "#2563EB";

    @Column(nullable = false)
    private String backgroundColor = "#F8FAFC";

    @Column(nullable = false)
    private String fontFamily = "'Segoe UI', sans-serif";

    @Column(nullable = false)
    private String textColor = "#1E293B";

    // CONSTRUCTORS
    public ThemeSetting() {}

    // GETTERS & SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(String primaryColor) { this.primaryColor = primaryColor; }

    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    public String getFontFamily() { return fontFamily; }
    public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }

    public String getTextColor() { return textColor; }
    public void setTextColor(String textColor) { this.textColor = textColor; }
}