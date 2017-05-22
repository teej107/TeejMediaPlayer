/**
 * Created by tanner on 5/22/17.
 */
class SongSort
{
    constructor(name, callback)
    {
        this.getSortName = () => name;
        this.getCallback = () => callback;
    }

    static titleSort()
    {
        return function (library)
        {

        }
    }
}

export default SongSort