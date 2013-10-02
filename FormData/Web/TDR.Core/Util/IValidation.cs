namespace TDR.Core.Util
{
    public interface IValidation<T> where T : class
    {
        ValidationResultInfo Validate(T itemToValidate);
    }
}
