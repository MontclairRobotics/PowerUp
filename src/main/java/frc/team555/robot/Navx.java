package frc.team555.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class Navx extends AHRS {

    public Navx(SPI.Port spi_port_id) {
        super(spi_port_id);
    }
}
