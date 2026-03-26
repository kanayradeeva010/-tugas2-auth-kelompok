package com.kelompok.biodata.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeSettingRepository extends JpaRepository<ThemeSetting, Long> {

}