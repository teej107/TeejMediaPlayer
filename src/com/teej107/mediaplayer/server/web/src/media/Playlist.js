/**
 * Created by Tanner Norton on 6/8/2017.
 */
class Playlist
{
    constructor()
    {
        var playlist, original = null;
        var index = -1;

        this.PlaybackModeEnum = Object.freeze({
            NORMAL: () =>
            {
                if (index + 1 >= playlist.length)
                    return -1;

                return (index + 1);
            },
            REPEAT: () => index,
            REPEAT_LIST: () =>
            {
                if (index + 1 >= playlist.length)
                    return 0;

                return (index + 1);
            }
        });

        var modeEnum = this.PlaybackModeEnum.NORMAL;
        this.playbackMode = (playbackModeEnum) =>
        {
            if (!playbackModeEnum)
                return modeEnum;

            modeEnum = playbackModeEnum;
        };

        this.shuffle = (bool) =>
        {
            if (bool === undefined)
                return !(original && original.shallowEquals(playlist));

            if (bool)
            {
                if (original)
                {
                    playlist = original.slice();
                    for (var i = playlist.length; i; i--)
                    {
                        var j = Math.floor(Math.random() * i);
                        [playlist[i - 1], playlist[j]] = [playlist[j], playlist[i - 1]];
                    }
                    return true;
                }
            }
            else
            {
                playlist = original;
            }
            return false;
        };

        this.setPlaylist = (songArr) =>
        {
            original = songArr;
            playlist = songArr;
            index = songArr ? 0 : -1;

            if (this.shuffle())
            {
                this.shuffle(true);
            }
        };

        this.setIndex = (i) =>
        {
            index = parseInt(i);
        };

        this.getPlaylist = () => playlist;
        this.getIndex = () => index;

        this.next = (pbMode) =>
        {
            if (!(playlist && playlist.length > 0))
                return null;

            index = pbMode instanceof Function ? pbMode() : modeEnum();
            if (index === -1)
                return null;

            return playlist[index];
        };

        this.previous = () =>
        {
            if (!(playlist && playlist.length > 0))
                return null;

            index = Math.max(index - 1, -1);
            if (index < 0)
            {
                if (modeEnum === this.PlaybackModeEnum.REPEAT_LIST)
                {
                    index = playlist.length - 1;
                    return playlist[index];
                }
                return null;
            }
            return playlist[index];
        };
    }
}

export default Playlist;
