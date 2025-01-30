using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class Payment
    {
        public int Pid { get; set; }
        public int OrderId { get; set; }
        public string RazorPayId { get; set; } = null!;
        public DateOnly PaymentDate { get; set; }
        public decimal Amount { get; set; }

        public virtual CustomerOrderLog Order { get; set; } = null!;
    }
}
