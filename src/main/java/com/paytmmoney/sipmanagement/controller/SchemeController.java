package com.paytmmoney.sipmanagement.controller;

import com.paytmmoney.sipmanagement.dto.SchemeDto;
import com.paytmmoney.sipmanagement.services.SchemeService;
import com.paytmmoney.sipmanagement.exceptions.DataUnavailableException;
import com.paytmmoney.sipmanagement.model.Scheme;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/Amcs/{amcId}/Schemes")
@Tag(name = "API for Scheme", description = "API's for managing scheme related operations")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "API for fetching All schemes of given amc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All schemes are fetched successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Given amc dosen't contain any amc", content=@Content)
    })
    @GetMapping
    public ResponseEntity<List<SchemeDto>> fetchAllSchemes(@Parameter(description = "AMC id for fetching schemes associated with given amc") @PathVariable String amcId){
        List<Scheme> schemesList = schemeService.fetchAllSchemes(amcId);
        if (schemesList.isEmpty())
            throw new DataUnavailableException("AMC with id:" + amcId + " contains no schemes");
        List<SchemeDto> schemeDtos = schemesList.stream()
                .map(scheme -> modelMapper.map(scheme, SchemeDto.class))
                .toList();
        return new ResponseEntity<>(schemeDtos, HttpStatus.OK);
    }

    @Operation(summary = "API for fetching unique scheme according to their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API fetched Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Table dosen't contain scheme with given id", content=@Content)
    })
    @GetMapping("/{schemeId}")
    public ResponseEntity<SchemeDto> fetchUniqueScheme(@Parameter(description = "Scheme id for fetching scheme") @PathVariable String schemeId, @PathVariable String amcId){
        return new ResponseEntity<>(modelMapper.map(schemeService.fetchUniqueScheme(schemeId, amcId),SchemeDto.class), HttpStatus.OK);
    }

    @Operation(summary = "API for creating new scheme in given amc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scheme Added Successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Scheme not added or problem in creating scheme", content=@Content)
    })
    @PostMapping
    public ResponseEntity<SchemeDto> createScheme(@Valid @RequestBody SchemeDto schemeDto, @Parameter(description = "AMC id for creating new scheme in given amc") @PathVariable String amcId){
        Scheme scheme = schemeService.createNewScheme(modelMapper.map(schemeDto,Scheme.class), amcId);
        return new ResponseEntity<>(modelMapper.map(scheme,SchemeDto.class),HttpStatus.CREATED);
    }

    @Operation(summary = "API for updating existing api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scheme updated Successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Scheme not updated", content=@Content)
    })
    @PutMapping("/{schemeId}")
    public ResponseEntity<SchemeDto> updateScheme(@Valid @RequestBody SchemeDto schemeDto, @Parameter(description = "Scheme id for updation") @PathVariable String schemeId, @PathVariable String amcId){
        Scheme updatedScheme = schemeService.updateScheme(modelMapper.map(schemeDto,Scheme.class),schemeId, amcId);
        return new ResponseEntity<>(modelMapper.map(updatedScheme,SchemeDto.class), HttpStatus.OK);
    }

    @Operation(summary = "API for deleting existing api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Scheme deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "No such scheme exist", content=@Content)
    })
    @DeleteMapping("/{schemeId}")
    public ResponseEntity<String> deleteExistingScheme(@Parameter(description = "Scheme id for deletion") @PathVariable String schemeId, @PathVariable String amcId){
        if (!schemeService.deleteScheme(schemeId, amcId)) {
            return new ResponseEntity<>("Problem in Deletion", HttpStatus.OK);
        }
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.BAD_REQUEST);
    }
}
