package com.xatu.system.domain.vo;

import com.xatu.common.domain.EnumResult;
import lombok.Data;

import java.util.List;

@Data
public class SelectionValue {
    List<EnumResult> schools;
    List<EnumResult> grades;
    List<EnumResult> periods;
    List<EnumResult> isOnlyMajors;
    List<EnumResult> assessments;
    List<EnumResult> states;
    List<EnumResult> weeks;
    List<EnumResult> hourPeriods;
}
