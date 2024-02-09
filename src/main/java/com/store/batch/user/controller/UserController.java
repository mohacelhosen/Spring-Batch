package com.store.batch.user.controller;

import com.store.batch.response.ApiResponse;
import com.store.batch.response.Error;
import com.store.batch.response.Success;
import com.store.batch.user.model.User;
import com.store.batch.user.service.UserService;
import com.store.batch.utils.Common;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @GetMapping("/start-batch")
    public  ResponseEntity<BatchStatus> startBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters  = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        JobExecution run = jobLauncher.run(job, jobParameters);
        BatchStatus status = run.getStatus();
        return ResponseEntity.ok(status);
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveLocalCsvToDB(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            Success<User> success = ApiResponse.success(
                    "/api/user",
                    Common.getRequestId(),
                    Common.getTimeStamp(),
                    savedUser,
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(success);
        } catch (IllegalArgumentException e) {
            Error error = ApiResponse.error(
                    "/api/user",
                    Common.getRequestId(),
                    Common.getTimeStamp(),
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Error error = ApiResponse.error(
                    "/api/user",
                    Common.getRequestId(),
                    Common.getTimeStamp(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
