import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;

public class Sounds
{
    static InputStream gun, subway, stone, error, pot , kill , alarm;
    static void initial()
    {
        try
        {
            InputStream gunStream = new FileInputStream("Gun.wav");
            gun = new AudioStream(gunStream);
            InputStream subwayStream = new FileInputStream("Subway.wav");
            subway = new AudioStream(subwayStream);
            InputStream stoneStream = new FileInputStream("Stone.wav");
            stone = new AudioStream(stoneStream);
            InputStream errorStream = new FileInputStream("Error.wav");
            error = new AudioStream(errorStream);
            InputStream potStream = new FileInputStream("Pot.wav");
            pot = new AudioStream(potStream);
            InputStream killStream = new FileInputStream("kill.wav");
            kill = new AudioStream(killStream);
            InputStream alarmStream = new FileInputStream("Alarm.wav");
            alarm = new AudioStream(alarmStream);


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void play(InputStream audioStream)
    {
        try
        {
            initial();
            AudioPlayer.player.start(audioStream);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
