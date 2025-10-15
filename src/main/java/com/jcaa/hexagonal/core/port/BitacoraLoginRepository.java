package com.jcaa.hexagonal.core.port;

import com.jcaa.hexagonal.core.domin.BitacoraLogin;
import java.util.List;

public interface BitacoraLoginRepository {
    BitacoraLogin save(BitacoraLogin bitacoraLogin);
    List<BitacoraLogin> findByUserId(Long userId);
    List<BitacoraLogin> findAll();
}

